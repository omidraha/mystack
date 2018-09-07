HTTP access control (CORS)
==========================

How does Access-Control-Allow-Origin header work?

Site B uses Access-Control-Allow-Origin to tell the browser that the content of this page is accessible to certain domains. By default, site B's pages are not accessible to any other domain; using the ACAO header opens a door for cross-domain access by specific domains.
Site B should serve its pages with:
Access-Control-Allow-Origin: http://sitea.com

Modern browsers will not block cross-domain requests outright. If site A requests a page from site B, the browser will actually fetch the requested page on the network level and check if the response headers list site A as a permitted requester domain. If site B has not indicated that site A is allowed to access this page, the browser will send an error and decide not to provide the response to the requesting JavaScript code.

EDIT: What happens on the network level is actually slightly more complex than I suggest here; there is sometimes a data-less "preflight" request when using special headers or HTTP verbs other than GET and POST (e.g. PUT, DELETE). See my answer on Understanding XMLHttpRequest over CORS for more details.

http://stackoverflow.com/a/10636765

Understanding XMLHttpRequest over CORS (responseText)
For a "simple" HTTP verb like GET or POST, yes, the entire page is fetched, and then the browser decides whether JavaScript gets to use the contents or not. The server doesn't need to know where the requests comes from; it is the browser's job to inspect the reply from the server and determine if JS is permitted to see the contents.

For a "non-simple" HTTP verb like PUT or DELETE, the browser issues a "preflight request" using an OPTIONS request. In that case, the browser first checks to see if the domain and the verb are supported, by checking for Access-Control-Allow-Origin and Access-Control-Allow-Methods, respectively. (See the "Handling a Not-So-Simple Request" on the CORS page of HTML5 Rocks for more information.) The preflight response also lists permissible non-simple headers, included in Access-Control-Allow-Headers.

This is because allowing a client to send a DELETE request to the server could be very bad, even if JavaScript never gets to see the cross-domain result -- again, remember that the server is generally not under any obligation to verify that the request is coming from a legitimate domain (although it may do so using the Origin header from the request).

http://stackoverflow.com/a/13400954/710446


https://developer.mozilla.org/en-US/docs/HTTP/Access_control_CORS

http://www.html5rocks.com/en/tutorials/cors/ 
