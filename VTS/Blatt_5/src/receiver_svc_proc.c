#include <rpc/rpc.h>
#include "pub_sub_deliv.h"
#include <stdio.h>

void *deliver_1_svc(delivery_t *delivery, struct svc_req *request){
    printf("%s: %s",delivery->topic,delivery->message);
}