Tips
====

PaaS (platform as a service)
----------------------------

https://openshift.redhat.com/

https://www.dotcloud.com/pricing.html

https://www.heroku.com/

http://www.paasify.it/compare/heroku-vs-openshift%20online

http://www.slideshare.net/Pivotal/paa-s-comparison2014v08

VPS Provider
------------

https://www.digitalocean.com/pricing/

https://www.vultr.com/pricing/

https://www.dreamhost.com/cloud/storage/

http://www.rackspace.com/cloud/servers

http://www.cloudvps.com/virtual-private-server/prices

https://www.transip.eu/vps/
https://www.transip.eu/demo-account/?landing=/cp/vps/
https://www.transip.eu/cp/vps/#vps-informatie
https://www.transip.eu/question/350-utilize-disk-space-for-bladevps/


Digital Ocean
-------------

http://www.scriptrock.com/articles/digitalocean-vs-aws

More storage option?
++++++++++++++++++++

https://www.digitalocean.com/community/questions/can-i-increase-storage

https://www.digitalocean.com/community/questions/more-storage-option

http://digitalocean.uservoice.com/forums/136585-digital-ocean/suggestions/3127077-extra-diskspace-

http://digitalocean.uservoice.com/forums/136585-digitalocean/suggestions/6662293-s3-object-storage-alternative

https://www.digitalocean.com/community/questions/getting-more-space-on-droplet

https://raymii.org/s/articles/Digital_Ocean_Sucks._Use_Digital_Ocean.html

http://venturebeat.com/2013/12/30/iaas-provider-digitalocean-finds-itself-back-in-security-trouble/

https://news.ycombinator.com/item?id=6764102

mysqldump --skip-extended-insert --all-databases --single-transaction --master-data=2 --flush-logs | gzip -9 --rsyncable > backup.sql.gz

sudo -u postgres pg_dumpall | gzip -9 --rsyncable > backup.sql.gz

Using object storage
++++++++++++++++++++

https://firefli.de/tutorials/s3fs-and-dreamobjects.html

http://www.maketecheasier.com/mount-amazon-s3-in-ubuntu/

Security issues
+++++++++++++++

https://github.com/fog/fog/issues/2525

http://seclists.org/fulldisclosure/2013/Aug/53

Others
++++++

http://rdiff-backup.nongnu.org/

http://www.rsync.net/products/pricing.html

https://github.com/vgough/encfs

https://github.com/s3fs-fuse/s3fs-fuse

Amazon
------

http://aws.amazon.com

http://aws.amazon.com/iam/

http://aws.amazon.com/s3/

http://aws.amazon.com/ec2/

http://aws.amazon.com/ec2/pricing/

http://aws.amazon.com/free/

http://aws.amazon.com/ec2/instance-types/

http://aws.amazon.com/elasticbeanstalk/

http://aws.amazon.com/elasticbeanstalk/pricing/

EC2 IaaS

Deploy django on amazon
+++++++++++++++++++++++

http://www.nickpolet.com/blog/deploying-django-on-aws/1/

http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/create_deploy_Python_django.html

http://agiliq.com/blog/2014/08/deploying-a-django-app-on-amazon-ec2-instance/

http://agiliq.com/blog/2009/03/django-with-mysql-and-apache-on-ec2/

http://thomas.broxrost.com/2008/08/21/persistent-django-on-amazon-ec2-and-ebs-the-easy-way/

http://pragmaticstartup.wordpress.com/2011/04/02/non-techie-guide-to-setting-up-django-apache-mysql-on-amazon-ec2/

http://www.philroche.net/archives/simple-django-install-on-amazon-ec2/

http://www.mlsite.net/blog/?p=43

http://michal.karzynski.pl/blog/2013/06/09/django-nginx-gunicorn-virtualenv-supervisor/



Blue Green Deployment
---------------------

http://martinfowler.com/bliki/BlueGreenDeployment.html


Continuous Delivery
-------------------

http://martinfowler.com/books/continuousDelivery.html


Continuous Integration
----------------------

http://martinfowler.com/articles/continuousIntegration.html

Feature toggle
--------------

http://code.flickr.net/2009/12/02/flipping-out/

https://en.wikipedia.org/wiki/Feature_toggle


Log collection service
----------------------

http://logstash.net/

https://papertrailapp.com/


How to configure Google Client Id and Google Client Secret?
-----------------------------------------------------------

https://console.developers.google.com/project

http://storeprestamodules.com/blog/how-to-configure-google-client-id-and-google-client-secret/

Kong with docker
----------------

.. code-block:: bash

    docker run --rm  --name kong-database \
                    -p 5432:5432 \
                    -e "POSTGRES_USER=kong" \
                    -e "POSTGRES_DB=kong" \
                    postgres:9.4


.. code-block:: bash

    docker run --rm  --name kong \
                  --link kong-database:kong-database \
                  -e "DATABASE=postgres" \
                  -p 8000:8000 \
                  -p 8443:8443 \
                  -p 8001:8001 \
                  -p 7946:7946 \
                  -p 7946:7946/udp \
                  --security-opt seccomp:unconfined \
                  mashape/kong

.. code-block:: bash

    curl -i -X GET \
      --url http://localhost:8000/ \
      --header 'Host: mockbin.com'

    curl -i -X POST \
      --url http://localhost:8001/apis/ \
      --data 'name=mockbin' \
      --data 'upstream_url=http://mockbin.com/' \
      --data 'request_host=mockbin.com'

    curl -i -X POST \
      --url http://localhost:8001/apis/mockbin/plugins/ \
      --data 'name=key-auth'

    curl -i -X POST \
      --url http://localhost:8001/consumers/ \
      --data "username=Jason"

    curl -i -X POST \
      --url http://localhost:8001/consumers/Jason/key-auth/ \
      --data 'key=ENTER_KEY_HERE'

    curl -i -X GET \
      --url http://localhost:8000 \
      --header "Host: mockbin.com" \
      --header "apikey: ENTER_KEY_HERE"

https://github.com/Mashape/kong/

Combine and minimize JavaScript, CSS and Images files
-----------------------------------------------------

https://github.com/mrclay/minify

https://github.com/yui/yuicompressor

https://github.com/django-compressor/django-compressor

https://github.com/jazzband/django-pipeline

https://samaxes.com/2009/05/combine-and-minimize-javascript-and-css-files-for-faster-loading/

https://robertnyman.com/2010/01/19/tools-for-concatenating-and-minifying-css-and-javascript-files-in-different-development-environments/

https://robertnyman.com/2010/01/15/how-to-reduce-the-number-of-http-requests/

http://www.revsys.com/12days/front-end-performance/

https://developers.google.com/speed/pagespeed/insights/?url=google.com

https://developers.google.com/speed/docs/insights/rules#speed-rules

Messaging
----------

https://seroter.com/2016/05/16/modern-open-source-messaging-apache-kafka-rabbitmq-and-nats-in-action/

https://taskqueues.com/

*   Delayed tasks

*   schedule recurring tasks, like a crontab

*   schedule tasks to execute at a given time, or after a given delay

*   automatically retry tasks that fail

*   Result storage

*   Automatic retries

Encrypting with Mozilla SOPS and AGE
------------------------------------


Encrypting `yaml/json/text` file with Mozilla SOPS and AGE

.. code-block:: bash

    sudo apt install age
    sudo dpkg -i sops.deb

    age-keygen -o key.txt

    ls
    cat key.txt

    echo "key: value" > env.yaml

    sops --encrypt --age  $(cat key.txt | grep -oP "public key: \K(.*)") env.yaml
    sops --encrypt --age  $(cat key.txt | grep -oP "public key: \K(.*)") env.yaml  > env.enc.yaml

    export EDITOR=vim
    export SOPS_AGE_KEY_FILE=key.txt

    sops env.enc.yaml

https://github.com/FiloSottile/age

https://github.com/getsops/sops

https://cloud.redhat.com/blog/a-guide-to-secrets-management-with-gitops-and-kubernetes

https://medium.com/@argonaut.dev/secret-management-in-kubernetes-approaches-tools-and-best-practices-f1df77392060

https://github.com/viaduct-ai/kustomize-sops


GitOps
------

https://www.weave.works/blog/gitops-operations-by-pull-request

