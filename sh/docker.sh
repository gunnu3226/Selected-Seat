#!/bin/bash

sudo docker system prune -a -f
yes | sudo docker image prune -a
