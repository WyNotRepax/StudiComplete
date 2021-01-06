#include <stdio.h>
#include <stdlib.h>
#include <rpc/rpc.h>
#include "pub_sub.h"
#include "pub_sub_deliv.h"
#include "return_codes.h"
#include <string.h>	// For memset()
#include <arpa/inet.h> // inet_ntoa
#include "sha_hashing.h"
#include <time.h>

#define FILENAME "hashes.txt"

#define ADDRSTRSIZE 64
#define MAXSUBSCRIBERS 128
#define MAXSESSIONS 128
#define LOCALHOST "127.0.0.1"

#define SESSION_INVALID -1
#define SESSION_WAITING 0
#define SESSION_VALID 1

static char* subscribers[MAXSUBSCRIBERS];
static int init = 0;
static int maxI = -1;
static char topic[MAXTOPICLEN] = "Default Topic";

typedef struct {
	sessionid_t sessionid;
	hashstring_t hash;
	int valid;
} session_t;

static int next_session_index = 0;
static session_t sessions[MAXSESSIONS];


int check_session(param_t*);
int create_session(hash_entry* entry);
session_t* find_session(sessionid_t sessionid);


void init_subscribers() {
	memset(subscribers, 0, sizeof(char*) * MAXSUBSCRIBERS);
	init = 1;
}

short* subscribe_1_svc(void* _NULL, struct svc_req* request)
{
	static short return_code;

	if (init == 0)
	{
		init_subscribers();
	}

	char* client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

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
	char* new_subscriber = (char*)malloc(sizeof(char) * ADDRSTRSIZE);
	memset(new_subscriber, '\00', sizeof(char) * ADDRSTRSIZE);
	strncpy(new_subscriber, client_address, ADDRSTRSIZE);
	subscribers[i] = new_subscriber;

	/* maxI ggf. anpasse */
	if (i > maxI) {
		maxI = i;
	}

	printf("%s subscribed @%d\n", new_subscriber, i);

	return_code = OK;
	return &return_code;
}

short* unsubscribe_1_svc(void* _NULL, struct svc_req* request) {
	static short return_code;

	if (init == 0)
	{
		init_subscribers();
	}

	char* client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

	for (int i = 0; i <= maxI; i++) {
		if (strncmp(subscribers[i], client_address, ADDRSTRSIZE) == 0)
		{
			free(subscribers[i]);
			subscribers[i] = (char*)0;

			printf("%s unsubscribed @%d\n", client_address, i);

			return_code = OK;
			return &return_code;
		}
	}

	return_code = CANNOT_UNREGISTER;
	return &return_code;
}

short* set_channel_1_svc(param_t* param, struct svc_req* request) {
	static short return_code;

	/* Authenticate the message */
	if (check_session(param) == 0) {
		return_code = AUTH_ERROR;
		return &return_code;
	}

	/* Check if a message has been send */
	if (param->arg.topic_or_message != 0) {
		return_code = WRONG_ARG_TYPE;
		return &return_code;
	}

	topic_t* new_topic = &(param->arg.argument_u.topic);

	char* client_address = inet_ntoa(request->rq_xprt->xp_raddr.sin_addr);

	if (strcmp(client_address, LOCALHOST) == 0) {
		strncpy(topic, *new_topic, MAXTOPICLEN);
		printf("New Topic set:%s\n", topic);
		return_code = OK;
		return &return_code;
	}

	return_code = CANNOT_SET_TOPIC;
	return &return_code;
}


extern  short* publish_1_svc(param_t* param, struct svc_req* request) {
	static short return_code;

	/* Authenticate the message */
	if (check_session(param) == 0) {
		return_code = AUTH_ERROR;
		return &return_code;
	}
	/* Check if a message has been send */
	if (param->arg.topic_or_message != 1) {
		return_code = WRONG_ARG_TYPE;
		return &return_code;
	}

	message_t* message = &(param->arg.argument_u.message);

	delivery_t delivery;
	delivery.message = *message;
	delivery.topic = topic;

	printf("Sending message: %s", *message);

	for (int i = 0; i <= maxI && i < MAXSUBSCRIBERS; i++) {
		char* server = subscribers[i];
		CLIENT* cl = clnt_create(server, PUBSUBDELIVPROG, PUBSUBDELIVVERS, "tcp");
		if (cl == NULL) {
			/* Verbindungsaufbau erfolglos  */
			clnt_pcreateerror(server);
		}
		else {
			deliver_1(&delivery, cl);
		}
	}

	return_code = OK;
	return &return_code;
}

extern sessionid_t* get_session_1_svc(user_t* user, struct svc_req* request) {

	static sessionid_t return_sessionid;
	/*Get List of Users*/
	hash_entry* hash_entrys;
	int max_idx = init_hash_digest(&hash_entrys);

	/*Get Entry for right User*/
	hash_entry* entry = NULL;
	for (int i = 0; i <= max_idx; i++) {
		if (strcmp(*user, hash_entrys[i].user) == 0) {
			entry = &hash_entrys[i];
		}
	}
	/*No Entry found*/
	if (entry == NULL) {
		printf("Could not find Entry for %s\n", *user);
		return_sessionid = 0;
		return &return_sessionid;
	}
	/*Create Session for User*/
	return_sessionid = create_session(entry);
	return &return_sessionid;
}

extern short* validate_1_svc(param_t* param, struct svc_req* request) {
	static short return_code;

	session_t* session = find_session(param->id);
	if (session == NULL) {
		return_code = AUTH_ERROR;
		return &return_code;
	}
	if (session->valid != SESSION_WAITING) {
		printf("Session is in wrong state for validation!\n");
		return_code = AUTH_ERROR;
		return &return_code;
	}
	/* Set session to valid for hash check*/
	session->valid = SESSION_VALID;
	if (check_session(param) == 0) {
		/* Set Session to invalid since the hash check Failed*/
		session->valid = SESSION_INVALID;
		return_code = AUTH_ERROR;
		return &return_code;
	}
	return_code = OK;
	return &return_code;

}

extern short* invalidate_1_svc(sessionid_t* sessionid, struct svc_req* request) {
	static short return_code;

	session_t* session = find_session(*sessionid);
	if (session == NULL) {
		return_code = AUTH_ERROR;
		return &return_code;
	}
	session->valid = SESSION_INVALID;
	return_code = OK;
	return &return_code;

}


int check_session(param_t* param) {
	session_t* session = find_session(param->id);
	if (session == NULL) {
		return 0;
	}
	if (session->valid != SESSION_VALID) {
		return 0;
	}
	char* str;
	switch (param->arg.topic_or_message)
	{
	case 0:
		str = param->arg.argument_u.topic;
		break;
	case 1:
		str = param->arg.argument_u.message;
		break;
	default:
		str = "";
		break;
	}
	char* hash = hash_sessionid_message_digest(session->sessionid, str, session->hash);
	if (strcmp(hash, param->hash) == 0) {
		return 1;
	}
	free(hash);
	return 0;
}



int create_session(hash_entry* entry) {

	/*Figure out next open index */
	int i = 0;
	while (sessions[i].valid >= 0 && i < MAXSESSIONS && i < next_session_index)
	{
		i++;
	}
	/* Maximale Anzahl von Sessions erreicht */
	if (i == MAXSESSIONS)
	{
		return -1;
	}

	sessions[i].hash = entry->hash;
	sessions[i].sessionid = clock();
	sessions[i].valid = SESSION_WAITING;


	if (i == next_session_index) {
		next_session_index++;
	}

	return sessions[i].sessionid;
}

session_t* find_session(sessionid_t sessionid) {
	for (int i = 0; i < next_session_index; i++) {
		if (sessions[i].sessionid == sessionid) {
			return &sessions[i];
		}
	}
	return NULL;
}