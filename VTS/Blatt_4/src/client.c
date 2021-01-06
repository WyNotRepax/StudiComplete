#include <stdio.h>
#include <rpc/rpc.h>
#include "pub_sub.h"
#include "return_codes.h"
#include <string.h>

#define COMMANDBUF 512

void pub_sub_subscribe(CLIENT *);
void pub_sub_unsubscribe(CLIENT *);
void pub_sub_publish(CLIENT *);
void pub_sub_set_channel(CLIENT *);
void pub_sub_help();
void pub_sub_exit();
void pub_sub_err(RET_CODE);

int main(int argc, char *argv[]){
    CLIENT *cl;
    char *server;

    if( argc != 2 ) {
        fprintf(stderr, "usage: %s host\n", argv[0]);
        exit(1);
    }

    /* Command line arguments */
    server = argv[1];

    /* Client handle erzeugen (mit TCP Protokoll) */
    cl = clnt_create(server, PUBSUBPROG, PUBSUBVERS, "tcp");
    if( cl == NULL ) {
        // Verbindungsaufbau erfolglos
        clnt_pcreateerror(server);
        exit(1);
    }
    /* Display Usage */
    printf("Pub Sub Client\n");
    printf("Type help for a list of commands\n");
    /* Main Program Loop */
    for(;;){
        char command[COMMANDBUF];
        printf("#");
        fgets(command,COMMANDBUF,stdin);
        if(strcmp(command,"subscribe\n") == 0){
            pub_sub_subscribe(cl);
        }else if(strcmp(command,"unsubscribe\n") == 0){
            pub_sub_unsubscribe(cl);
        }else if(strcmp(command,"publish\n") == 0){
            pub_sub_publish(cl);
        }else if(strcmp(command,"set_channel\n") == 0){
            pub_sub_set_channel(cl);
        }else if(strcmp(command,"help\n") == 0){
            pub_sub_help();
        }else if(strcmp(command,"exot\n") == 0){
            pub_sub_exit();
        } else{
            printf("Unknown Command!\n");
            printf("Type help for a list of commands\n");
        }
    }

    return 0;
}

void pub_sub_subscribe(CLIENT *cl){
    short* return_code;

    return_code = subscribe_1(NULL,cl);

    if(return_code == NULL){
        /* Fehler beim rpc aufruf */
        clnt_perror(cl, "Error");
        exit(1);
    }

    if(*return_code != 0){
        /* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
        pub_sub_err(*return_code);
    }else{
        /* Aufruf erfolgreich und kein Fehler ist aufgetreten */
        printf("Successfully Subscribed!\n");
    }
}

void pub_sub_unsubscribe(CLIENT *cl){
    short* return_code;

    return_code = unsubscribe_1(NULL,cl);

    if(return_code == NULL){
        /* Fehler beim rpc aufruf */
        clnt_perror(cl, "Error");
        exit(1);
    }

    if(*return_code != 0){
        /* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
        pub_sub_err(*return_code);
    }else{
        /* Aufruf erfolgreich und kein Fehler ist aufgetreten */
        printf("Successfully Unsubscribed!\n");
    }
}

void pub_sub_publish(CLIENT *cl){
    short* return_code;

    
    char* message = (char*) malloc(MAXMESSAGELEN);
    memset(message, '\00', MAXMESSAGELEN);

    /* Get Message from user */
    printf("Message:");
    fgets(message,MAXMESSAGELEN,stdin);

    /* Call remote Procedure */
    return_code = publish_1(&message, cl);
    
    if(return_code == NULL){
        /* Fehler beim rpc aufruf */
        clnt_perror(cl, "Error");
        exit(1);
    }

    if(*return_code != 0){
        /* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
        pub_sub_err(*return_code);
    }else{
        /* Aufruf erfolgreich und kein Fehler ist aufgetreten */
        printf("Message send: %s",message);
    }
    free(message);
}

void pub_sub_set_channel(CLIENT *cl){
    short* return_code;

    char* topic = (char*) malloc(MAXTOPICLEN);
    memset(topic, '\00', MAXTOPICLEN);

    /* Get Message from user */
    printf("New Channel Topic:");
    fgets(topic,MAXTOPICLEN,stdin);

    if(strlen(topic) <= 0){
        free(topic);
        return;
    }

    /* remove trailing newline */
    topic[strlen(topic)-1] = '\00';
    
    /* Call remote Procedure */
    return_code = set_channel_1(&topic, cl);
    
    if(return_code == NULL){
        /* Fehler beim rpc aufruf */
        clnt_perror(cl, "Error");
        exit(1);
    }

    if(*return_code != 0){
        /* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
        pub_sub_err(*return_code);
    }else{
        /* Aufruf erfolgreich und kein Fehler ist aufgetreten */
        printf("Topic set to %s\n",topic);
    }

    free(topic);
}

void pub_sub_help(){
    printf("Commands:\n");
    printf("subscribe\n");
    printf("unsubscribe\n");
    printf("publish\n");
    printf("set_channel\n");
    printf("help\n");
    printf("exit\n");
}

void pub_sub_exit(){
    printf("Bye!\n");
    exit(0);
}

void pub_sub_err(RET_CODE return_code){
    if(return_code >= 0 && return_code <= 5){
        fprintf(stderr,"Error: %s\n",PUB_SUB_RET_CODE[return_code]);
    }else{
        fprintf(stderr,"Error: Unkown Error Code: %d\n",return_code);
    }
}
