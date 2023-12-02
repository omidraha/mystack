SCIM
====

System for Cross-domain Identity Management: Core Schema

https://www.rfc-editor.org/rfc/rfc7643.html


Customize user provisioning attribute-mappings
----------------------------------------------


https://learn.microsoft.com/en-gb/entra/identity/app-provisioning/customize-application-attributes#what-you-should-know


Custom extension attribute to a SCIM compliant application
-----------------------------------------------------------

https://learn.microsoft.com/en-gb/entra/identity/app-provisioning/customize-application-attributes#editing-the-list-of-supported-attributes


User Principal Name
--------------------

A UPN must be unique among all security principal objects within a directory forest.
This means the prefix of a UPN can be reused, just not with the same suffix.

The userPrincipalName attribute is the logon name for the user.
The attribute consists of a user principal name (UPN), which is the most common logon name for Windows users.
Users typically use their UPN to log on to a domain. This attribute is an indexed string that is single-valued.


A UPN consists of a UPN prefix (the user account name) and a UPN suffix (a DNS domain name).
The prefix is joined with the suffix using the "@" symbol. For example, "someone@ example.com".


https://learn.microsoft.com/en-us/windows/win32/ad/naming-properties#userprincipalname

Design your user and group schema
---------------------------------

https://learn.microsoft.com/en-us/entra/identity/app-provisioning/use-scim-to-provision-users-and-groups

"Group" Resource Schema
-----------------------

https://datatracker.ietf.org/doc/html/rfc7643#section-4.2
