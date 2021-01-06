#include <stdio.h>
#include <rpc/rpc.h>
#include "pub_sub.h"
#include "pub_sub_deliv.h"
#include "return_codes.h"
#include <string.h>	// For memset()
#include <arpa/inet.h> // inet_ntoa

#define ADDRSTRSIZE 64
#define MAXSUBSCRIBERS 128
#define LOCALHOST "127.0.0.1"

static char *subscribers[MAXSUBSCRIBERS];
static int init = 0;
static int maxI = 0;
static char topic[MAXTOPICLEN] = "Default Topic";

void init_subscribers(){
    memset(subscribers, 0, sizeof(char*) * MAXSUBSCRIBERS);
	init = 1;
}

short* subscribe_1_svc(void *_NULL, struct svc_req *request)
{
	static short return_code;
    
	if (init == 0)
	{
        init_subscribers();
	}

	char *client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

	/*Check if client is already registered */
	for (int i = 0; i <= maxI; i++)
	{
		if (subscribers[i] != NULL)
		{
			if (strncmp(subscribers[i], client_address, ADDRSTRSIZE) == 0)
			{
				return_code = CLIENT_ALREADY_REGISTERED;
				return &return_code;
			}
		}
	}

	/*Figure out next open index */
	;
	int i = 0;
	while (subscribers[i] != NULL && i < MAXSUBSCRIBERS)
	{
		i++;
	}

    /* Maximale Anzahl von Subscribern erreicht */ 
	if (i == MAXSUBSCRIBERS)
	{
		return_code = CANNOT_REGISTER;
		return &return_code;
	}

    /* Neuen subscriber in die Liste eintragen */
	char *new_subscriber = (char*) malloc(sizeof(char) * ADDRSTRSIZE);
	memset(new_subscriber, '\00', sizeof(char) * ADDRSTRSIZE);
	strncpy(new_subscriber, client_address, ADDRSTRSIZE);
    subscribers[i] = new_subscriber;

    /* maxI ggf. anpasse */
    if(i > maxI){
        maxI = i;
    }

    printf("%s subscribed @%d\n",new_subscriber,i);

    return_code = OK;
    return &return_code;
}

short *unsubscribe_1_svc(void *_NULL,struct svc_req *request){
	static short return_code;

    if (init == 0)
	{
        init_subscribers();
	}

	char *client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

    for(int i = 0; i <= maxI; i++){
		if (strncmp(subscribers[i], client_address, ADDRSTRSIZE) == 0)
			{
				free(subscribers[i]);
				subscribers[i] = (char*)0;

    			printf("%s unsubscribed @%d\n",client_address,i);

				return_code = OK;
				return &return_code;
			}
	}

	return_code = CANNOT_UNREGISTER;
	return &return_code;
}

short *set_channel_1_svc(topictype *new_topic, struct svc_req *request){
	static short return_code;
	
	char *client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

	if(strcmp(client_address,LOCALHOST) == 0){
		strncpy(topic,*new_topic,MAXTOPICLEN);
		printf("New Topic set:%s\n",topic);
		return_code = OK;
		return &return_code;
	}
	
	return_code = CANNOT_SET_TOPIC;
	return &return_code;
}


extern  short * publish_1_svc(messagetype *message, struct svc_req *request){
	static short return_code;
	delivertype delivery;
	delivery.message = *message;
	delivery.topic = topic;

	printf("Sending message: %s",*message);

	for(int i = 0; i <= maxI && i < MAXSUBSCRIBERS; i++)	{
		char* server = subscribers[i];
		CLIENT *cl = clnt_create(server, PUBSUBDELIVPROG, PUBSUBDELIVVERS, "tcp");
		if( cl == NULL ) {
        	/* Verbindungsaufbau erfolglos  */
        	clnt_pcreateerror(server);
    	}else{
			deliver_1(&delivery,cl);
		}
	}

	return_code = OK;
	return &return_code;
}