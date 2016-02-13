(use 'hiccup.core)

;; ****************************************************************
;; The basics
;; ****************************************************************

(html [:p]) 
  => "<p />"

;; Any elements after that become the content of the tag
(html [:p "hey"]) 
  => "<p>hey</p>"

;; Tags can be nested inside of tags and everything ends up concatenated
(html [:p "Hello " [:em "world"]])
  => "<p>Hello <em>world</em></p>"

;; You can specify attributes by supplying a map after the tag name
(html [:p {:id "my-p"} "hey"])
  => "<p id=\"my-p\">hey</p>"

;; There are shortcuts for setting ID and class, if you know CSS, 
;; these should look familiar
(html [:p#my-p [:span.pretty "hey"]])
  => "<p id=\"my-p\"><span class=\"pretty\">hey</span></p>"

;; You can escape a string using the (escape-html) function
(html [:p (escape-html "<script>Do something evil</script>")])
  => "<p>&lt;script&gt;Do something evil&lt;/script&gt;</p>"

;; the h function is a shortcut for (escapte-html)
(html [:p (h "<script>Do more evil</script>")])
  => "<p>&lt;script&gt;Do more evil&lt;/script&gt;</p>"

;; you can actually generate generic xml too
(html [:books 
       [:book#142 {:title "Noir for beginners"}]])
  => "<books><book id=\"142\" title=\"Noir for beginners\" /></books>"

;; ****************************************************************
;; Page helpers
;; ****************************************************************

(use 'hiccup.page-helpers)

;; you can use the html5, html4, or xhtml functions to write the 
;; doctype boilerplate for you
(html (html5 [:p "hey"]))
  => "<!DOCTYPE html>\n<html><p>hey</p></html>"

;; there are helpers for including css and js files
(html (include-js "/js/core.js")
      (include-css "/css/reset.css"))
  =>"<script src=\"/js/core.js\" type=\"text/javascript\"></script>
     <link href=\"/css/reset.css\" rel=\"stylesheet\" type=\"text/css\" />"

;; there are also functions for creating links and images
(html (link-to "http://www.webnoir.org" "Noir")
      (mail-to "cool@cool.com")
      (image "/img/logo.png" "Noir"))
  => "<a href=\"http://www.webnoir.org\">Noir</a>
      <a href=\"mailto:cool@cool.com\">cool@cool.com</a>
      <image alt=\"Noir\" src=\"/img/logo.png\" />"

;; these functions can take maps to add custom attributes too
(html (link-to {:class "pretty"} "http://www.webnoir.org" "Noir"))
  => "<a class=\"pretty\" href=\"http://www.webnoir.org\">Noir</a>"

;; ****************************************************************
;; Form helpers
;; ****************************************************************

(use 'hiccup.form-helpers)

;; form helpers help you write the boilerplate for creating fields.
;; There are functions for all the different html input types.
(html (form-to [:post "/login"]
               (text-field "Username")
               (password-field "Password")
               (submit-button "Login")))
  => "<form action=\"/login\" method=\"POST\">
     <input id=\"Username\" name=\"Username\" type=\"text\" />
     <input id=\"Password\" name=\"Password\" type=\"password\" />
     <input type=\"submit\" value=\"Login\" />
     </form>"

;; the fields can take initial values as well
(html (text-field "Username" "chris"))
  => "<input id=\"Username\" name=\"Username\" type=\"text\" 
      value=\"chris\" />"
