const DELIVMAXTOPICLEN = 512;
const DELIVMAXMESSAGELEN = 8192;

typedef string delivtopic_t<DELIVMAXTOPICLEN>;
typedef string delivmessage_t<DELIVMAXMESSAGELEN>;

struct delivery_s{
    delivtopic_t topic;
    delivmessage_t message;
};
typedef struct delivery_s delivery_t;

/* the pub_sub program definition */
program PUBSUBDELIVPROG {
    version PUBSUBDELIVVERS {
        void DELIVER(delivery_t) = 1;
    } = 1;
} = 0x20425301;