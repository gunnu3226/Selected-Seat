sudo docker rm -f $(docker ps -aq)

docker pull yiyaaa/selected-seat
docker run -d --name selected-seat -p 80:8080 yiyaaa/selected-seat

docker-compose down

sleep 5

docker-compose up -d

sudo docker image prune -f
echo "배포성공!!!"
