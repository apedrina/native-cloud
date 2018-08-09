#!/bin/bash
./rabbitmqadmin declare queue --vhost=/ name=some_outgoing_queue durable=true
