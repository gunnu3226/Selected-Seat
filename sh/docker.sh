#!/bin/bash

echo "aws 연결"
#
#random_string=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 10 ; echo '')
#
#echo "랜덤 문자열: $random_string"

sudo aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 851725481726.dkr.ecr.ap-northeast-2.amazonaws.com

echo "도커 image파일을 만듭니다."
docker build -t selected-seat:latest .

echo "도커 기존 이미지 삭제"
aws ecr batch-delete-image --repository-name selected-seat-test --image-ids imageTag=latest


echo "도커 이미지를 ECR에 태그합니다."
docker tag selected-seat:latest 851725481726.dkr.ecr.ap-northeast-2.amazonaws.com/selected-seat-test:latest
#
echo "도커를 ECR에 push합니다."
docker push 851725481726.dkr.ecr.ap-northeast-2.amazonaws.com/selected-seat-test:latest

echo "ecs 업데이트"
aws ecs update-service --cluster selected-cluster --service selected-seat-service --force-new-deployment


echo "도커 이미지 정리"
sudo docker system prune -a -f
yes | sudo docker image prune -a

