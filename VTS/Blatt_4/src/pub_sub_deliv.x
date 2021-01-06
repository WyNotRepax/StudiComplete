const DELIVMAXTOPICLEN = 512;
const DELIVMAXMESSAGELEN = 8192;

typedef string delivtopictype<DELIVMAXTOPICLEN>;
typedef string delivmessagetype<DELIVMAXMESSAGELEN>;

struct delivertype{
    delivtopictype topic;
    delivmessagetype message;
};

/* the pub_sub program definition */
program PUBSUBDELIVPROG {
    version PUBSUBDELIVVERS {
        void DELIVER(delivertype) = 1;
    } = 1;
} = 0x20425301;