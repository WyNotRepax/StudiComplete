#include <stdio.h>
#include <rpc/rpc.h>
#include "pub_sub.h"
#include "return_codes.h"
#include <string.h>
#include <openssl/sha.h>
#include "sha_hashing.h"

#define COMMANDBUF 512

void pub_sub_subscribe(CLIENT*);
void pub_sub_unsubscribe(CLIENT*);
void pub_sub_publish(CLIENT*);
void pub_sub_set_channel(CLIENT*);
void pub_sub_help();
void pub_sub_exit(CLIENT *);
void pub_sub_err(RET_CODE);
void pub_sub_login(CLIENT*);
void pub_sub_logout(CLIENT*);

char user[USERLEN];
char hash[HASHLEN];
int sessionid;

int main(int argc, char* argv[]) {
	CLIENT* cl;
	char* server;

	if (argc != 2) {
		fprintf(stderr, "usage: %s host\n", argv[0]);
		exit(1);
	}

	/* Command line arguments */
	server = argv[1];

	/* Client handle erzeugen (mit TCP Protokoll) */
	cl = clnt_create(server, PUBSUBPROG, PUBSUBVERS, "tcp");
	if (cl == NULL) {
		// Verbindungsaufbau erfolglos
		clnt_pcreateerror(server);
		exit(1);
	}
	/* Display Usage */
	printf("Pub Sub Client\n");
	printf("Type help for a list of commands\n");
	/* Main Program Loop */
	for (;;) {
		char command[COMMANDBUF];
		printf("#");
		fgets(command, COMMANDBUF, stdin);
		if (strcmp(command, "subscribe\n") == 0) {
			pub_sub_subscribe(cl);
		}
		else if (strcmp(command, "unsubscribe\n") == 0) {
			pub_sub_unsubscribe(cl);
		}
		else if (strcmp(command, "publish\n") == 0) {
			pub_sub_publish(cl);
		}
		else if (strcmp(command, "set_channel\n") == 0) {
			pub_sub_set_channel(cl);
		}
		else if (strcmp(command, "help\n") == 0) {
			pub_sub_help();
		}
		else if (strcmp(command, "exit\n") == 0) {
			pub_sub_exit(cl);
		}
		else if (strcmp(command, "login\n") == 0) {
			pub_sub_login(cl);
		}
		else {
			printf("Unknown Command!\n");
			printf("Type help for a list of commands\n");
		}
	}

	return 0;
}

void pub_sub_subscribe(CLIENT* cl) {
	short* return_code;

	return_code = subscribe_1(NULL, cl);

	if (return_code == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}

	if (*return_code != 0) {
		/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
		pub_sub_err(*return_code);
	}
	else {
		/* Aufruf erfolgreich und kein Fehler ist aufgetreten */
		printf("Successfully Subscribed!\n");
	}
}

void pub_sub_unsubscribe(CLIENT* cl) {
	short* return_code;

	return_code = unsubscribe_1(NULL, cl);

	if (return_code == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}

	if (*return_code != 0) {
		/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
		pub_sub_err(*return_code);
	}
	else {
		/* Aufruf erfolgreich und kein Fehler ist aufgetreten */
		printf("Successfully Unsubscribed!\n");
	}
}

void pub_sub_publish(CLIENT* cl) {
	short* return_code;


	char* message = (char*)malloc(MAXMESSAGELEN);
	memset(message, '\00', MAXMESSAGELEN);

	/* Get Message from user */
	printf("Message:");
	fgets(message, MAXMESSAGELEN, stdin);

	/* Param erzeugen */
	char* message_hash = hash_sessionid_message_digest(sessionid, message, hash);
	param_t param;
	param.arg.topic_or_message = 1;
	param.arg.argument_u.message = message;
	param.hash = message_hash;
	param.id = sessionid;

	/* Call remote Procedure */
	return_code = publish_1(&param, cl);

	if (return_code == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}

	if (*return_code != 0) {
		/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
		pub_sub_err(*return_code);
	}
	else {
		/* Aufruf erfolgreich und kein Fehler ist aufgetreten */
		printf("Message send: %s", message);
	}
	free(message);
}

void pub_sub_set_channel(CLIENT* cl) {
	short* return_code;

	char* topic = (char*)malloc(MAXTOPICLEN);
	memset(topic, '\00', MAXTOPICLEN);

	/* Get Message from user */
	printf("New Channel Topic:");
	fgets(topic, MAXTOPICLEN, stdin);

	if (strlen(topic) <= 0) {
		free(topic);
		return;
	}

	/* remove trailing newline */
	topic[strlen(topic) - 1] = '\00';


	/* Param erzeugen */
	char* topic_hash = hash_sessionid_message_digest(sessionid, topic, hash);
	param_t param;
	param.arg.topic_or_message = 0;
	param.arg.argument_u.topic = topic;
	param.hash = topic_hash;
	param.id = sessionid;

	/* Call remote Procedure */
	return_code = set_channel_1(&param, cl);

	if (return_code == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}

	if (*return_code != 0) {
		/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
		pub_sub_err(*return_code);
	}
	else {
		/* Aufruf erfolgreich und kein Fehler ist aufgetreten */
		printf("Topic set to %s\n", topic);
	}

	free(topic);
}

void pub_sub_help() {
	printf("Commands:\n");
	printf("login\n");
	printf("logout\n");
	printf("subscribe\n");
	printf("unsubscribe\n");
	printf("publish\n");
	printf("set_channel\n");
	printf("help\n");
	printf("exit\n");
}

void pub_sub_exit(CLIENT* cl) {
	pub_sub_logout(cl);
	printf("Bye!\n");
	exit(0);
}

void pub_sub_err(RET_CODE return_code) {
	if (return_code >= 0 && return_code <= 7) {
		fprintf(stderr, "Error: %s\n", PUB_SUB_RET_CODE[return_code]);
	}
	else {
		fprintf(stderr, "Error: Unkown Error Code: %d\n", return_code);
	}
}

void pub_sub_login(CLIENT* cl) {
	short* return_code;
	sessionid_t* sessionid_p;
	/* Reset/Setup Strings */
	memset(user, '\00', USERLEN);
	memset(user, '\00', HASHLEN);

	char userbuff[COMMANDBUF];
	char pwdbuff[COMMANDBUF];

	memset(userbuff, '\00', COMMANDBUF);
	memset(pwdbuff, '\00', COMMANDBUF);

	/* Read input */
	printf("Username: ");
	fgets(userbuff, COMMANDBUF, stdin);
	printf("Password: ");
	fgets(pwdbuff, COMMANDBUF, stdin);

	/*Remove trailing newline*/;
	userbuff[strlen(userbuff) - 1] = '\00';
	pwdbuff[strlen(pwdbuff) - 1] = '\00';
	strncpy(user, userbuff, USERLEN);
	char* hash_temp = hash_user_pwd(user, pwdbuff);
	strcpy(hash, hash_temp);
	free(hash_temp);

	printf("Set Credentials to %s %s\n", user, hash);

	char* tmp = user;
	sessionid_p = get_session_1((char**)(&tmp), cl);

	if (sessionid_p == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}
	sessionid = *sessionid_p;
	if (sessionid == 0) {
		printf("User was not found on Server!\n");
	}
	else {
		param_t param;

		char* validate_hash = hash_sessionid_message_digest(sessionid, "", hash);

		param.arg.topic_or_message = -1;
		param.id = sessionid;
		param.hash = validate_hash;

		return_code = validate_1(&param, cl);

		free(validate_hash);

		if (return_code == NULL) {
			/* Fehler beim rpc aufruf */
			clnt_perror(cl, "Error");
			exit(1);
		}

		if (*return_code != 0) {
			/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
			pub_sub_err(*return_code);
		}
		else {
			printf("Successfully Logged in!\n");
		}
	}

}

void pub_sub_logout(CLIENT* cl) {
	short* return_code;

	return_code = invalidate_1(&sessionid, cl);

	if (return_code == NULL) {
		/* Fehler beim rpc aufruf */
		clnt_perror(cl, "Error");
		exit(1);
	}

	if (*return_code != 0) {
		/* Rpc aufruf erfolgreich aber ein anderer Fehler ist aufgetreten */
		pub_sub_err(*return_code);
	}
	else {
		/* Aufruf erfolgreich und kein Fehler ist aufgetreten */
		printf("Successfully logged out!\n");
	}
}