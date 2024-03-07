# Workshop 1: Docker Compose

### The `docker-compose.yml` file

This file contains everything you need to run kafka locally.

To spin it up, you will need to have `docker-compose` installed, and do the following:

- in your terminal, `cd` to this directory (ie. where the `docker-compose.yml` file is)
- run the following command: `docker-compose up -d`

You should now have a running kafka cluster on your machine.

#### Breakdown of the file

`services`: this is where we define containers. 

This file has 4 containers: `kafka-0`, `kafka-1`, `kafka-2` and `kafka-ui`

Each container has some properties:

- `image` is the image of this container.
- `container_name` is the container's name, ie. how you'll refer to it in the CLI. If this isn't specified, docker will give it a random name.
- `ports` defines what network ports should be open between the container and the host. We need this in order to let network traffic through. If not specified, then docker will block all traffic by default.
- `environment` here we set the environment variables inside the container. This is typically used to give some extra configuration options for the applications inside the container.
- `volumes` this binds data volumes to the container. E.g. the container `kafka-2` has the following declaration: `kafka_2_data:/bitnami/kafka`, which means: bind the volume by the name of `kafka_2_data` to the directory `/bitnami/kafka` inside the container.

The volumes themselves are defined at the root level with the `volumes` key. We need volumes when we want to persist data in a container, because a container's file-system will reset every time the container is stopped and started. When we define volumes, they will keep data between container restarts.