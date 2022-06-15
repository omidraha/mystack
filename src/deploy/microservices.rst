Microservices
=============


Multiple Services Per Host
--------------------------

Benefits:

First, purely from a host management point of view, it is simpler. In a world
where one team manages the infrastructure and another team manages the software, the
infrastructure team’s workload is often a function of the number of hosts it has to manage.
If more services are packed on to a single host, the host management workload doesn’t
increase as the number of services increases.

Second is cost. Even if you have access to a
virtualization platform that allows you to provision and resize virtual hosts, the
virtualization can add an overhead that reduces the underlying resources available to your
services. In my opinion, both these problems can be addressed with new working practices
and technology, and we’ll explore that shortly.

This model is also familiar to those who deploy into some form of an application
container. In some ways, the use of an application container is a special case of the
multiple-services-per-host model.

This model can also simplify the life of the developer.
Deploying multiple services to a single host in
production is synonymous with deploying multiple services to a local dev workstation or
laptop. If we want to look at an alternative model, we want to find a way to keep this
conceptually simple for developers.

Challenges:

First, it can make monitoring more difficult. For example, when tracking CPU,
do I need to track the CPU of one service
independent of the others? Or do I care about the CPU of the box as a whole?

Side effects also be hard to avoid. If one service is under significant load, it can end up reducing
the resources available to other parts of the system.

Gilt, when scaling out the number of
services it ran, hit this problem. Initially it coexisted many services on a single box, but
uneven load on one of the services would have an adverse impact on everything else
running on that host.

This makes impact analysis of host failures more complex as well —
taking a single host out of commission can have a large ripple effect.

Deployment of services can be somewhat more complex too, as ensuring one deployment
doesn’t affect another leads to additional headaches. For example, if I use Puppet to
prepare a host, but each service has different (and potentially contradictory) dependencies,
how can I make that work? In the worst-case scenario, I have seen people tie multiple
service deployments together, deploying multiple different services to a single host in one
step, to try to simplify the deployment of multiple services to one host.

In my opinion, the small upside in improving simplicity is more than outweighed
by the fact that we have
given up one of the key benefits of microservices: striving for independent release of our
software. If you do adopt the multiple-services-per-host model, make sure you keep hold
of the idea that each service should be deployed independently.

This model can also inhibit autonomy of teams. If services for different teams are installed
on the same host, who gets to configure the host for their services? In all likelihood, this
ends up getting handled by a centralized team, meaning it takes more coordination to get
services deployed.

Another issue is that this option can limit our deployment artifact options. Image-based
deployments are out, as are immutable servers unless you tie multiple different services
together in a single artifact, which we really want to avoid.

The fact that we have multiple services on a single host means that efforts to target scaling
to the service most in need of it can be complicated. Likewise, if one microservice handles
data and operations that are especially sensitive, we might want to set up the underlying
host differently, or perhaps even place the host itself in a separate network segment.
Having everything on one host means we might end up having to treat all services the
same way even if their needs are different.


Single Service Per Host
-----------------------

With a single-service-per-host model, we avoid side effects of
multiple hosts living on a single host, making monitoring and remediation much simpler.
We have potentially reduced our single points of failure. An outage to one host should
impact only a single service, although that isn’t always clear when you’re using a
virtualized platform. We also can more easily scale one service independent from others,
and deal with security concerns more easily by focusing our attention
only on the service and host that requires it.

Having an increased number of hosts has potential downsides, though. We have more
servers to manage, and there might also be a cost implication of running more distinct
hosts. Despite these problems, this is still the model I prefer for microservice
architectures.

Mantl
-----

From my understanding, Mantl is a collection of tools/applications that ties together
to create a cohesive docker-based application platform. Mantl is ideally deployed on
virtualized/cloud environments (AWS, OpenStack, GCE), but I have just recently able to
deploy it on bare-metal.
The main component in Mantl is Mesos, which manages dockers, handles scheduling and
task isolation. Marathon is a mesos framework that manages long running tasks, such as web services,
this is where most application reside. The combination of mesos-marathon handles application high-availability, resiliency and load-balancing. Tying everything together is consul, which handles service discovery. I use consul to do lookups for each application to communication to each other. Mantl also includes the ELK stack for logging, but I haven't had any success in monitoring any of my applications, yet. There is also Chronos, where scheduled tasks are handles ala cron. Traefik acts as a reverse-proxy, where application/service endpoints are mapped to URLs for external services to communicate.
Basically, your microservices should be self-contained in docker images,
initiate communications via consul lookup and logs into standard io. Then you deploy your app, using the Marathon API, and monitor it in Marathon UI. When deploying your dockerized-app, marathon will register you docker image names in consul, along with its' exposed port. Scheduled tasks should be deployed in Chronos, where you will be able to monitor running tasks and pending scheduled tasks.

http://stackoverflow.com/questions/35267071/how-microservices-are-managed-using-mantl

http://www.infoq.com/news/2016/02/cisco-mantl-microservices

https://sreeninet.wordpress.com/2016/03/25/microservices-infrastructure-using-mantl/

https://fak3r.com/2015/05/27/howto-build-microservices-infrastructure-with-mantl/


.. code-block:: bash

    $ git clone https://github.com/CiscoCloud/mantl.git
    $ cd mantl
    $ pip install -r requirements.txt
    $ ./security-setup
    $ vagrant up
    $ vagrant status

Now browse to https://192.168.100.101

To create new container:

.. code-block:: bash

    $ curl -k -X POST -H "Content-Type: application/json" -u "admin:1" -d@"examples/hello-world/hello-world.json" "https://192.168.100.101/marathon/v2/apps"

Mantl uses Mesos as the Orchestration layer.

lattice
-------

http://lattice.cf/docs/getting-started/

vamp
----
http://vamp.io/

calico
------

https://www.projectcalico.org/getting-started/docker/

https://github.com/projectcalico/calico


marathon
--------

https://github.com/mesosphere/marathon

terraform
---------

https://www.terraform.io/intro/
