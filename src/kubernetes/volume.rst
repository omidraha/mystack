Volume
======


Types of Volumes
Persistent Volume
Persistent Volume Claim
Dynamic provisioning of volumes

https://kubernetes.io/docs/concepts/storage/persistent-volumes/#types-of-persistent-volumes


https://medium.com/geekculture/storage-kubernetes-92eb3d027282

Storage class
-------------

https://kubernetes.io/docs/concepts/storage/storage-classes/#provisioner


Dynamic provisioning
--------------------

With dynamic provisioning,
you do not have to create a PV object. Instead,
it will be automatically created under the hood when you create the PVC.
Kubernetes does so using another object called Storage Class


A Storage Class is an abstraction that defines a class of backend persistent storage ,
for example, `Amazon EFS file storage`, `Amazon EBS block storage`, etc. used for container applications.

A Storage Class essentially contains two things:

`Name`: This is the name, which uniquely identifies the storage class object.

`Provisioner`: This defines the underlying storage technology.
For example, provisioner would be `efs.csi.aws.com` for Amazon EFS` or `ebs.csi.aws.com` for `Amazon EBS`.


https://aws.amazon.com/blogs/storage/persistent-storage-for-kubernetes/
