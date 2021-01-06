#ifndef SHA_HASHING_H
#define SHA_HASHING_H


typedef struct {
	char* user;
	char* hash;
} hash_entry;

int init_hash_digest(hash_entry **);
int s_getline(char* str, int l, FILE* f);
char* hash_sha(char* str);
char* hash_user_pwd(char* user, char* pwd);
char* hash_sessionid_message_digest(int sessionid, char* message, char* digest);

#endif 