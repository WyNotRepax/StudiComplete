#include <rpc/rpc.h>
#include "pub_sub_deliv.h"
#include <stdio.h>

void *deliver_1_svc(delivertype *delivery, struct svc_req *request){
    printf("%s: %s",delivery->topic,delivery->message);
}