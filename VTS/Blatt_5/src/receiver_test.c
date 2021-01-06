
#include <stdio.h>
#include <rpc/rpc.h>
#include "pub_sub_deliv.h"


int main(int argc, char *argv[]){
    CLIENT *cl;
    char* server;
    char* topic;
    char* message;
    delivertype* delivery;

    if( argc != 4 ) {
        fprintf(stderr, "usage: %s host topic message", argv[0]);
        exit(1);
    }
    
    /* Command line arguments */
    server = argv[1];
    topic = argv[2];
    message = argv[3];

    /* Client handle erzeugen (mit TCP Protokoll) */
    cl = clnt_create(server, PUBSUBDELIVPROG, PUBSUBDELIVVERS, "tcp");
    if( cl == NULL ) {
        /* Verbindungsaufbau erfolglos  */
        clnt_pcreateerror(server);
        exit(1);
    }

    
    /* Parameter initialisieren */
    delivery = (delivertype *)malloc(sizeof(delivertype));
    delivery->message = strdup(message);
    delivery->topic = strdup(topic);

    /* Debug output */
    printf("Topic: %s, Message: %s\n",delivery->topic,delivery->message);

    /* Eigentlicher Aufruf der entfernten Prozedur */ 
    deliver_1(delivery,cl);



    return 0;
}
