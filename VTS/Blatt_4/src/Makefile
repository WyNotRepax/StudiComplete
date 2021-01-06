ALL = receiver client dispatcher
RPCGEN = rpcgen

RPCGEN_pub_sub = pub_sub_clnt.c pub_sub_svc.c pub_sub_xdr.c pub_sub.h
RPCGEN_pub_sub_deliv = pub_sub_deliv_clnt.c pub_sub_deliv_svc.c pub_sub_deliv_xdr.c pub_sub_deliv.h

all: $(ALL)

$(RPCGEN_pub_sub): pub_sub.x
	$(RPCGEN) pub_sub.x

$(RPCGEN_pub_sub_deliv): pub_sub_deliv.x
	$(RPCGEN) pub_sub_deliv.x

client: client.c pub_sub_clnt.c pub_sub_xdr.c pub_sub.h return_codes.h
	gcc -o client pub_sub_xdr.c pub_sub_clnt.c client.c

dispatcher: dispatcher_svc_proc.c pub_sub_svc.c pub_sub_xdr.c pub_sub.h pub_sub_deliv_clnt.c pub_sub_deliv_xdr.c pub_sub_deliv.h return_codes.h
	gcc -o dispatcher dispatcher_svc_proc.c pub_sub_xdr.c pub_sub_svc.c pub_sub_deliv_clnt.c pub_sub_deliv_xdr.c

receiver: pub_sub_deliv_svc.c pub_sub_deliv_xdr.c pub_sub_deliv.h receiver_svc_proc.c
	gcc -o receiver receiver_svc_proc.c pub_sub_deliv_xdr.c pub_sub_deliv_svc.c

receiver_test: receiver receiver_test.c pub_sub_deliv_clnt.c pub_sub_deliv_xdr.c pub_sub_deliv.h 
	gcc -o receiver_test receiver_test.c pub_sub_deliv_clnt.c pub_sub_deliv_xdr.c

clean:
	$(RM) *.o  client dispatcher receiver receiver_test $(RPCGEN_pub_sub) $(RPCGEN_pub_sub_deliv) 
