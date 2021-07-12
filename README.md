sudo docker tag {CONTAINER_HASH} registry.heroku.com/wfwbf-docker/web
sudo docker push registry.heroku.com/wfwbf-docker/web
heroku container:release web --app wfwbf-docker

heroku ps:scale web=0 --app wfwbf-docker

heroku restart --app wfwbf-docker

-Xms70m -Xmx70m -XX:MaxPermSize=25m 

