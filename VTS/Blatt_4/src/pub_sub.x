const MAXTOPICLEN = 512;
const MAXMESSAGELEN = 8192;

typedef string topictype<MAXTOPICLEN>;
typedef string messagetype<MAXMESSAGELEN>;

/* the pub_sub program definition */
program PUBSUBPROG {
    version PUBSUBVERS {
        short SUBSCRIBE(void) = 1;
        short UNSUBSCRIBE(void) = 2;
        short SET_CHANNEL(topictype) = 3;
        short PUBLISH(messagetype) = 4;
    } = 1;
} = 0x20425300;