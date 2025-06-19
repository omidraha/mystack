Restoring MongoDB Atlas Cloud Data to a Local Docker Environment
================================================================

In this guide, we will walk through the steps required to restore MongoDB data from an **Atlas Cloud Database** to a local **Docker** environment.
This is useful for development and testing purposes, as it allows you to work with a copy of your production data in a controlled, local environment.

Prerequisites
-------------

Before we proceed, ensure you have the following:

1. **Docker** installed and running on your local machine.
2. **MongoDB Atlas Cloud Backup** (or data dump) ready for restoration.

Steps
------

1. **Prepare MongoDB Atlas Backup**

   If you're using MongoDB Atlas, you can export a backup of your data through the Atlas UI. Follow these steps in Atlas:

   1. Go to your **MongoDB Atlas Console**.
   2. Select your **Cluster**.
   3. Navigate to the **Backups** section.
   4. Export a backup of your desired database.

2. **Set Up a Docker Container with MongoDB**

   To restore data locally, you first need to run a MongoDB container using Docker.
    Ensure you're using the same version of MongoDB as your Atlas Cloud database (for example, MongoDB 7.0.20).
    You can specify the version with the mongo:7.0.20 image.

Run the following command to start the MongoDB container with the correct version:

   .. code:: bash

      docker run -d \
            --name mongo \
            -p 27017:27017 \
            -v /home/user/data/db:/data/db \
            mongo:7.0.20

  - `--name mongo`: Names the container `mongo`.
  - `-d`: Runs the container in detached mode.
  - `-p 27017:27017`: Maps port 27017 from the container to your host machine, which is the default port for MongoDB.
  - `-v /home/user/data/db:/data/db`: Mounts your local backup data directory (`/home/user/data/db`)
    into the container’s data directory (`/data/db`), ensuring MongoDB can access the data.

3. **Verify MongoDB is Running Locally**

   Once the container is up, you can verify that MongoDB is running by executing:

   .. code:: bash

      docker ps

   This will list all running containers, including the MongoDB container.

4. **Verify the Restoration**

    To check the restoration, view the MongoDB logs:

   .. code:: bash

      docker logs mongo

   You can also verify the restoration by listing the collections in the database.
    To do so, connect to the MongoDB instance using the Mongo shell:

   .. code:: bash

      docker exec -it mongo mongosh

   This command connects you to the MongoDB shell inside the container.

   List all databases:

      .. code:: bash

         show dbs

   Use the restored database:

      .. code:: bash

         use MyDB

   List collections:

      .. code:: bash

         show collections

   Count all collections:

      .. code:: js

        var collections = db.getCollectionNames();
        collections.forEach(function(collection) {
            var count = db.getCollection(collection).countDocuments();
            print(collection + ": " + count);
        });


   If everything was restored correctly, you should see all your collections.

8. **Troubleshooting**

   * **Permissions Issues**: Ensure the backup files have the correct permissions before copying them into the container.
    If necessary, change the ownership or permissions using `chmod` or `chown`.
   * **MongoDB Version Compatibility**: Ensure the MongoDB version in your Docker container is compatible with the version of MongoDB used in your Atlas Cloud instance.
    This can prevent potential issues with data format incompatibilities.

9. **Cleaning Up**

   After the restoration is complete and verified, you can stop and remove the MongoDB Docker container if you no longer need it:

   .. code:: bash

      docker stop mongo
      docker rm mongo

   This will stop and remove the container. If you plan to use MongoDB again, you can always restart the container using `docker start mongo`.

Conclusion
----------

By following these steps, you can easily restore MongoDB data from your Atlas Cloud instance to a local Docker environment.
This setup is useful for local testing, development, and debugging while keeping your data safe and secure in a production-like setup.
If you encounter any issues, make sure to check the MongoDB logs inside the Docker container for more details.
