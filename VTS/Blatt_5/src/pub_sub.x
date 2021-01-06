const MAXTOPICLEN = 512;
const MAXMESSAGELEN = 8192;

const USERLEN = 12;
const HASHLEN = 96; /* bei SHA 256 */

typedef string topic_t<MAXTOPICLEN>;
typedef string message_t<MAXMESSAGELEN>;

typedef string hashstring_t <HASHLEN>; /* Parameter */
typedef string user_t <USERLEN>; /* User */

typedef int sessionid_t;

typedef short ret_code_t;

union argument switch (int topic_or_message) {
    case 0: topic_t topic; /* topic wird uebertragen */
    case 1: message_t message; /* message wird uebertragen */
    default: void; /* kein Parameter */
};

struct param_s {
    sessionid_t id;
    argument arg;
    hashstring_t hash;
};
typedef struct param_s param_t;

/* the pub_sub program definition */
program PUBSUBPROG {
    version PUBSUBVERS {
        ret_code_t SUBSCRIBE(void) = 1;
        ret_code_t UNSUBSCRIBE(void) = 2;
        ret_code_t SET_CHANNEL(param_t) = 3;
        ret_code_t PUBLISH(param_t) = 4;
        sessionid_t GET_SESSION (user_t) = 5;
        ret_code_t VALIDATE (param_t) = 6;
        ret_code_t INVALIDATE (sessionid_t) = 7;
    } = 1;
} = 0x20425300;