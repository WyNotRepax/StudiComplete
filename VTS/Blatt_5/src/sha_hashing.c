/**************************************************************
 * sha_hashing.c
 *
 * Nutzung der SHA256 Funktion.
 * Kompilieren mit: gcc -o -g ... -lssl -lcrypto
 *
 * Heinz-Josef Eikerling, HS OS
 * 7.04.2017: Fertigstellung
 *
 **************************************************************/

 // MODIFIZIERT

#include <stdio.h>
#include <stdlib.h>

#include <string.h>
#include <openssl/sha.h>
#include "sha_hashing.h"

#define TRUE 1
#define FALSE 0

#define USERLEN 12
#define PWDLEN 12
#define HASHLEN 100

/* Datenstruktur zur Repraesentation des Hash-Digest als Feld */
#define MAX_HASH_DIGEST_LENGTH 100


/*
 * Sicheres Lesen von f (z.B. stdin).
 * Die Eingabezeile wird vollständig gelesen, aber nur l
 * Zeichen werden in str geschrieben. Speicher fuer str muss allokiert
 * worden sein.
 */
int s_getline(char* str, int l, FILE* f) {
	if (l > 256) {
		fprintf(stderr, "s_getline(): Error with string length.");
		str[0] = '\0';
		return FALSE;
	}
	char tmp_str[256];
	if (!fgets(tmp_str, 256 - 1, f)) return FALSE;
	for (int i = 0; i < (l - 1); i++) {
		char c = tmp_str[i];
		if ((c == '\n') || (c == '\0')) {
			str[i] = '\0';
			return TRUE;
		}
		else str[i] = c;
	}
	return TRUE;
}

/*
 * Lesen des Hash-Digest 'hashes.txt'.
 * Eintraege wird in einer globalen Variablen gespeichert.
 */
int init_hash_digest(hash_entry** hash_entrys) {
	static int hash_digest_initialized = FALSE;
	static hash_entry hash_digest[MAX_HASH_DIGEST_LENGTH];
	static int max_idx = -1;

	*hash_entrys = hash_digest;

	/* Initialisierung erfolgt nur einmal */
	if (hash_digest_initialized == TRUE) return max_idx;
	/* else */
	FILE* f = fopen("hashes.txt", "r");
	if (f == NULL) {
		fprintf(stderr, "Cannot open digest 'hashes.txt'.\n");
		return max_idx;
	}
	/* else */
	size_t MAX_LINE_LENGTH = 120;
	char line[MAX_LINE_LENGTH];
	while (s_getline(line, MAX_LINE_LENGTH, f) == TRUE) {
		char user[USERLEN + 1];
		char hash[HASHLEN + 1];
		int n = sscanf(line, "%s %s", user, hash);
		if (n == 2) {
			max_idx++;
			hash_digest[max_idx].user = strdup(user);
			hash_digest[max_idx].hash = strdup(hash);
			printf("%d: %s %s\n", max_idx, user, hash);
		}
		else {
			fprintf(stderr, "Line '%s' malformatted!\n", line);
		}
	}
	fclose(f);
	hash_digest_initialized = TRUE;
	printf("Hash digest 'hashes.txt' read.\n");
	return max_idx;
}

/* Hashfunktionen mit verschiedenen Parametern */
char* hash_sha(char* str) {
	unsigned char result[SHA256_DIGEST_LENGTH];
	SHA256(str, strlen(str), result);
	char hash_val[96];
	hash_val[0] = '\0';
	for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
		char tmp[3];
		sprintf(tmp, "%02x", result[i]);
		strcat(hash_val, tmp);
	}
	// printf ("hashval: %s\n", hash_val);
	return strdup(hash_val);
}

/*
 * Hash aus user und pwd bilden -> dieser muss im Server-Digest gespeichert
 * werden.
 */
char* hash_user_pwd(char* user, char* pwd) {
	char str[256];
	sprintf(str, "%s;%s", user, pwd);
	return hash_sha(str);
}

char* hash_sessionid_message_digest(int sessionid, char* message, char* digest) {
	char str[8192];
	sprintf(str, "%d;%s;%s", sessionid, message, digest);
	return hash_sha(str);
}

