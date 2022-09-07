window.SlagoModel={};
//=>./js/lib/Slagolib.js
/*!
 * Slago.js v0.0.1
 * (c) 2021-2021 Wanlu Gao
 * Released under the MIT License.
 * GitHub  https://github.com/gaowanlu/Slago.js
 */
if (window.Slagolib == undefined) {
    console.log("🍔HELLO Slago.js v0.0.1")
    window.Slagolib = (function () {
        //create namespace for build content of window.Slago
        let namespace = {};
        //模板引擎
        namespace.template = {
            /*Scanner is class for Scanner Object
             *constructor parameter:
             *   template: template String
             */
            Scanner: function (template) {
                this.template = template; //copy template

                this._pointer = 0; //location pointer

                this._tail = this.template; //string that is not scanned

                this.showTemplate = function () {
                    console.log(this.template); //print template string to console
                };

                this.scan = function (flag) { //flag is stop_flag of scan
                    if (this._tail.indexOf(flag) == 0) {
                        //the pointer moves the flag.length backward
                        this._pointer += flag.length;
                        //update _tail
                        this._tail = this.template.substr(this._pointer);
                    }
                };

                this.scanUntil = function (flag) {
                    let start_index = this._pointer;
                    while (this._tail.indexOf(flag) != 0 && !this._over()) {
                        this._pointer++; //move backforward
                        this._tail = this.template.substr(this._pointer);
                    }
                    //return start_index now:_pointer
                    return this.template.substr(start_index, this._pointer - start_index);
                };

                //judge this._tail is null?
                this._over = function () {
                    if (this._pointer >= this.template.length) {
                        return true;
                    } else {
                        return false;
                    }
                };
            }, //END-Scanner class

            /*parseTokens:get nest tokens array
             *build nesttokens
             *function prameter:
             *   template:template string
             */
            parseTokens: function (template) {
                let tokens = [];
                //create scanner
                let scanner = new this.Scanner(template);
                while (!scanner._over()) {
                    let before_flag_words = scanner.scanUntil("{{");
                    tokens.push(['text', before_flag_words]);
                    //step flag.length to _pointer
                    scanner.scan("{{");
                    //get key from {{key}}
                    let center_words = scanner.scanUntil("}}");
                    //delete center_words before and afterblanksapce
                    center_words.replace(/^\s+|\s+$/g, "");
                    switch (center_words[0]) {
                        case '/':
                            tokens.push(['/', center_words.substr(1)]);
                            break;
                        case '#':
                            tokens.push(['#', center_words.substr(1)]);
                            break;
                        case '^':
                            tokens.push(['^', center_words.substr(1)]);
                            break;
                        default:
                            if (center_words) {
                                tokens.push(['name', center_words]);
                            }
                    }
                    scanner.scan("}}");
                }
                //nest tokens  using this.nestTokens function
                return this.nestTokens(tokens);
            },

            /*nestTokens:to nest tokens
             *function parameter:
             *   tokens:tokens
             */
            nestTokens: function (tokens) {
                let nestToken = [];
                let operateStack = [];
                //all push stack
                operateStack.push(nestToken);
                for (let i = 0; i < tokens.length; i++) {
                    //stack is null
                    if (operateStack.length == 0) {
                        break;
                    }
                    switch (tokens[i][0]) {
                        case '#':
                        case '^':
                            operateStack[operateStack.length - 1].push(tokens[i]);
                            tokens[i].push([]);
                            operateStack.push(tokens[i][2]);
                            break;

                        case '/':
                            //出栈
                            operateStack.pop();
                            break;

                        default:
                            //text直接入栈
                            operateStack[operateStack.length - 1].push(tokens[i]);
                            break;
                    }
                }
                return nestToken;
            },

            /*lookup function
             *get object property using stirng to index
             */
            lookup: function (data, keyWords) {
                if (!data || !keyWords) return "";
                //delete before after blankspace
                let deleteBlank = keyWords.replace(/^\s+|\s+$/g, "");

                if (keyWords.indexOf(".") != -1 && keyWords != '.') {
                    let keyWordsArray = deleteBlank.split(".");

                    let temp = data;

                    for (let i = 0; i < keyWordsArray.length; i++) {
                        temp = temp[keyWordsArray[i]];
                    }

                    return temp;
                }

                return data[keyWords];
            },


            /*parseHTML
             *tokens transform HTMLString
             *function parameter:
             *   tokens:nest type tokens
             *   data:template data
             */
            parseHTML: function (tokens, data) {
                let domString = "";

                for (let i = 0; i < tokens.length; i++) {
                    let array = tokens[i];
                    switch (array[0]) {
                        case "text":
                            domString += array[1];
                            break;

                        case "name":
                            domString += this.lookup(data, array[1].toString());
                            break;

                        case "#":
                            domString += this.parseArray(array, data);
                            break;
                        case "^":
                            if (this.lookup(data, array[1].toString())) {
                                domString += this.parseHTML(array[2], data);
                            }
                            break;
                        default:
                            break;
                    }
                }

                return domString;
            },

            /*parseArray
            *process array data，recursion with parseHTML
            *   token such :["#",'student',[]]
            */
            parseArray:function(token,data){
                let resultString = ""; 
                //get the array you want to iterate over
                let circleArray = this.lookup(data, token[1]);

                for (let i = 0; i < circleArray.length; i++) {
                    //Object to merge,merge{'.':circleArray[i]} and circleArray[i]
                    let tempObj = {
                        '.': circleArray[i]
                    };
                    for (let key in circleArray[i]) {
                        tempObj[key] = circleArray[i][key];
                    }
                    resultString += this.parseHTML(token[2], tempObj);
                }
                //{...circleArray[i],'.': circleArray[i]}
                return resultString;
            },

            /*engin：it is interface about template object
             *function parameter:
             *   template:template string
             *   data:template data
             */
            engin: function (template, data) {
                //judge temp is null?
                if (!template || !data || typeof (template) != "string" || typeof (data) != "object") {
                    return ""; //reutrn blank string
                }
                //start engin work
                //get HTMLString nestTokens,data
                return this.parseHTML(this.parseTokens(template), data);
            }
        };//END-namespace.template

        return namespace; //namespace content into window.Slago
    })();
} else {
    console.log("🤣window.Slago already exists");
}
//=>./js/lib/axios.js
/* axios v0.21.1 | (c) 2020 by Matt Zabriskie */
!function(e,t){"object"==typeof exports&&"object"==typeof module?module.exports=t():"function"==typeof define&&define.amd?define([],t):"object"==typeof exports?exports.axios=t():e.axios=t()}(this,function(){return function(e){function t(r){if(n[r])return n[r].exports;var o=n[r]={exports:{},id:r,loaded:!1};return e[r].call(o.exports,o,o.exports,t),o.loaded=!0,o.exports}var n={};return t.m=e,t.c=n,t.p="",t(0)}([function(e,t,n){e.exports=n(1)},function(e,t,n){"use strict";function r(e){var t=new i(e),n=s(i.prototype.request,t);return o.extend(n,i.prototype,t),o.extend(n,t),n}var o=n(2),s=n(3),i=n(4),a=n(22),u=n(10),c=r(u);c.Axios=i,c.create=function(e){return r(a(c.defaults,e))},c.Cancel=n(23),c.CancelToken=n(24),c.isCancel=n(9),c.all=function(e){return Promise.all(e)},c.spread=n(25),c.isAxiosError=n(26),e.exports=c,e.exports.default=c},function(e,t,n){"use strict";function r(e){return"[object Array]"===R.call(e)}function o(e){return"undefined"==typeof e}function s(e){return null!==e&&!o(e)&&null!==e.constructor&&!o(e.constructor)&&"function"==typeof e.constructor.isBuffer&&e.constructor.isBuffer(e)}function i(e){return"[object ArrayBuffer]"===R.call(e)}function a(e){return"undefined"!=typeof FormData&&e instanceof FormData}function u(e){var t;return t="undefined"!=typeof ArrayBuffer&&ArrayBuffer.isView?ArrayBuffer.isView(e):e&&e.buffer&&e.buffer instanceof ArrayBuffer}function c(e){return"string"==typeof e}function f(e){return"number"==typeof e}function p(e){return null!==e&&"object"==typeof e}function d(e){if("[object Object]"!==R.call(e))return!1;var t=Object.getPrototypeOf(e);return null===t||t===Object.prototype}function l(e){return"[object Date]"===R.call(e)}function h(e){return"[object File]"===R.call(e)}function m(e){return"[object Blob]"===R.call(e)}function y(e){return"[object Function]"===R.call(e)}function g(e){return p(e)&&y(e.pipe)}function v(e){return"undefined"!=typeof URLSearchParams&&e instanceof URLSearchParams}function x(e){return e.replace(/^\s*/,"").replace(/\s*$/,"")}function w(){return("undefined"==typeof navigator||"ReactNative"!==navigator.product&&"NativeScript"!==navigator.product&&"NS"!==navigator.product)&&("undefined"!=typeof window&&"undefined"!=typeof document)}function b(e,t){if(null!==e&&"undefined"!=typeof e)if("object"!=typeof e&&(e=[e]),r(e))for(var n=0,o=e.length;n<o;n++)t.call(null,e[n],n,e);else for(var s in e)Object.prototype.hasOwnProperty.call(e,s)&&t.call(null,e[s],s,e)}function E(){function e(e,n){d(t[n])&&d(e)?t[n]=E(t[n],e):d(e)?t[n]=E({},e):r(e)?t[n]=e.slice():t[n]=e}for(var t={},n=0,o=arguments.length;n<o;n++)b(arguments[n],e);return t}function j(e,t,n){return b(t,function(t,r){n&&"function"==typeof t?e[r]=S(t,n):e[r]=t}),e}function C(e){return 65279===e.charCodeAt(0)&&(e=e.slice(1)),e}var S=n(3),R=Object.prototype.toString;e.exports={isArray:r,isArrayBuffer:i,isBuffer:s,isFormData:a,isArrayBufferView:u,isString:c,isNumber:f,isObject:p,isPlainObject:d,isUndefined:o,isDate:l,isFile:h,isBlob:m,isFunction:y,isStream:g,isURLSearchParams:v,isStandardBrowserEnv:w,forEach:b,merge:E,extend:j,trim:x,stripBOM:C}},function(e,t){"use strict";e.exports=function(e,t){return function(){for(var n=new Array(arguments.length),r=0;r<n.length;r++)n[r]=arguments[r];return e.apply(t,n)}}},function(e,t,n){"use strict";function r(e){this.defaults=e,this.interceptors={request:new i,response:new i}}var o=n(2),s=n(5),i=n(6),a=n(7),u=n(22);r.prototype.request=function(e){"string"==typeof e?(e=arguments[1]||{},e.url=arguments[0]):e=e||{},e=u(this.defaults,e),e.method?e.method=e.method.toLowerCase():this.defaults.method?e.method=this.defaults.method.toLowerCase():e.method="get";var t=[a,void 0],n=Promise.resolve(e);for(this.interceptors.request.forEach(function(e){t.unshift(e.fulfilled,e.rejected)}),this.interceptors.response.forEach(function(e){t.push(e.fulfilled,e.rejected)});t.length;)n=n.then(t.shift(),t.shift());return n},r.prototype.getUri=function(e){return e=u(this.defaults,e),s(e.url,e.params,e.paramsSerializer).replace(/^\?/,"")},o.forEach(["delete","get","head","options"],function(e){r.prototype[e]=function(t,n){return this.request(u(n||{},{method:e,url:t,data:(n||{}).data}))}}),o.forEach(["post","put","patch"],function(e){r.prototype[e]=function(t,n,r){return this.request(u(r||{},{method:e,url:t,data:n}))}}),e.exports=r},function(e,t,n){"use strict";function r(e){return encodeURIComponent(e).replace(/%3A/gi,":").replace(/%24/g,"$").replace(/%2C/gi,",").replace(/%20/g,"+").replace(/%5B/gi,"[").replace(/%5D/gi,"]")}var o=n(2);e.exports=function(e,t,n){if(!t)return e;var s;if(n)s=n(t);else if(o.isURLSearchParams(t))s=t.toString();else{var i=[];o.forEach(t,function(e,t){null!==e&&"undefined"!=typeof e&&(o.isArray(e)?t+="[]":e=[e],o.forEach(e,function(e){o.isDate(e)?e=e.toISOString():o.isObject(e)&&(e=JSON.stringify(e)),i.push(r(t)+"="+r(e))}))}),s=i.join("&")}if(s){var a=e.indexOf("#");a!==-1&&(e=e.slice(0,a)),e+=(e.indexOf("?")===-1?"?":"&")+s}return e}},function(e,t,n){"use strict";function r(){this.handlers=[]}var o=n(2);r.prototype.use=function(e,t){return this.handlers.push({fulfilled:e,rejected:t}),this.handlers.length-1},r.prototype.eject=function(e){this.handlers[e]&&(this.handlers[e]=null)},r.prototype.forEach=function(e){o.forEach(this.handlers,function(t){null!==t&&e(t)})},e.exports=r},function(e,t,n){"use strict";function r(e){e.cancelToken&&e.cancelToken.throwIfRequested()}var o=n(2),s=n(8),i=n(9),a=n(10);e.exports=function(e){r(e),e.headers=e.headers||{},e.data=s(e.data,e.headers,e.transformRequest),e.headers=o.merge(e.headers.common||{},e.headers[e.method]||{},e.headers),o.forEach(["delete","get","head","post","put","patch","common"],function(t){delete e.headers[t]});var t=e.adapter||a.adapter;return t(e).then(function(t){return r(e),t.data=s(t.data,t.headers,e.transformResponse),t},function(t){return i(t)||(r(e),t&&t.response&&(t.response.data=s(t.response.data,t.response.headers,e.transformResponse))),Promise.reject(t)})}},function(e,t,n){"use strict";var r=n(2);e.exports=function(e,t,n){return r.forEach(n,function(n){e=n(e,t)}),e}},function(e,t){"use strict";e.exports=function(e){return!(!e||!e.__CANCEL__)}},function(e,t,n){"use strict";function r(e,t){!s.isUndefined(e)&&s.isUndefined(e["Content-Type"])&&(e["Content-Type"]=t)}function o(){var e;return"undefined"!=typeof XMLHttpRequest?e=n(12):"undefined"!=typeof process&&"[object process]"===Object.prototype.toString.call(process)&&(e=n(12)),e}var s=n(2),i=n(11),a={"Content-Type":"application/x-www-form-urlencoded"},u={adapter:o(),transformRequest:[function(e,t){return i(t,"Accept"),i(t,"Content-Type"),s.isFormData(e)||s.isArrayBuffer(e)||s.isBuffer(e)||s.isStream(e)||s.isFile(e)||s.isBlob(e)?e:s.isArrayBufferView(e)?e.buffer:s.isURLSearchParams(e)?(r(t,"application/x-www-form-urlencoded;charset=utf-8"),e.toString()):s.isObject(e)?(r(t,"application/json;charset=utf-8"),JSON.stringify(e)):e}],transformResponse:[function(e){if("string"==typeof e)try{e=JSON.parse(e)}catch(e){}return e}],timeout:0,xsrfCookieName:"XSRF-TOKEN",xsrfHeaderName:"X-XSRF-TOKEN",maxContentLength:-1,maxBodyLength:-1,validateStatus:function(e){return e>=200&&e<300}};u.headers={common:{Accept:"application/json, text/plain, */*"}},s.forEach(["delete","get","head"],function(e){u.headers[e]={}}),s.forEach(["post","put","patch"],function(e){u.headers[e]=s.merge(a)}),e.exports=u},function(e,t,n){"use strict";var r=n(2);e.exports=function(e,t){r.forEach(e,function(n,r){r!==t&&r.toUpperCase()===t.toUpperCase()&&(e[t]=n,delete e[r])})}},function(e,t,n){"use strict";var r=n(2),o=n(13),s=n(16),i=n(5),a=n(17),u=n(20),c=n(21),f=n(14);e.exports=function(e){return new Promise(function(t,n){var p=e.data,d=e.headers;r.isFormData(p)&&delete d["Content-Type"];var l=new XMLHttpRequest;if(e.auth){var h=e.auth.username||"",m=e.auth.password?unescape(encodeURIComponent(e.auth.password)):"";d.Authorization="Basic "+btoa(h+":"+m)}var y=a(e.baseURL,e.url);if(l.open(e.method.toUpperCase(),i(y,e.params,e.paramsSerializer),!0),l.timeout=e.timeout,l.onreadystatechange=function(){if(l&&4===l.readyState&&(0!==l.status||l.responseURL&&0===l.responseURL.indexOf("file:"))){var r="getAllResponseHeaders"in l?u(l.getAllResponseHeaders()):null,s=e.responseType&&"text"!==e.responseType?l.response:l.responseText,i={data:s,status:l.status,statusText:l.statusText,headers:r,config:e,request:l};o(t,n,i),l=null}},l.onabort=function(){l&&(n(f("Request aborted",e,"ECONNABORTED",l)),l=null)},l.onerror=function(){n(f("Network Error",e,null,l)),l=null},l.ontimeout=function(){var t="timeout of "+e.timeout+"ms exceeded";e.timeoutErrorMessage&&(t=e.timeoutErrorMessage),n(f(t,e,"ECONNABORTED",l)),l=null},r.isStandardBrowserEnv()){var g=(e.withCredentials||c(y))&&e.xsrfCookieName?s.read(e.xsrfCookieName):void 0;g&&(d[e.xsrfHeaderName]=g)}if("setRequestHeader"in l&&r.forEach(d,function(e,t){"undefined"==typeof p&&"content-type"===t.toLowerCase()?delete d[t]:l.setRequestHeader(t,e)}),r.isUndefined(e.withCredentials)||(l.withCredentials=!!e.withCredentials),e.responseType)try{l.responseType=e.responseType}catch(t){if("json"!==e.responseType)throw t}"function"==typeof e.onDownloadProgress&&l.addEventListener("progress",e.onDownloadProgress),"function"==typeof e.onUploadProgress&&l.upload&&l.upload.addEventListener("progress",e.onUploadProgress),e.cancelToken&&e.cancelToken.promise.then(function(e){l&&(l.abort(),n(e),l=null)}),p||(p=null),l.send(p)})}},function(e,t,n){"use strict";var r=n(14);e.exports=function(e,t,n){var o=n.config.validateStatus;n.status&&o&&!o(n.status)?t(r("Request failed with status code "+n.status,n.config,null,n.request,n)):e(n)}},function(e,t,n){"use strict";var r=n(15);e.exports=function(e,t,n,o,s){var i=new Error(e);return r(i,t,n,o,s)}},function(e,t){"use strict";e.exports=function(e,t,n,r,o){return e.config=t,n&&(e.code=n),e.request=r,e.response=o,e.isAxiosError=!0,e.toJSON=function(){return{message:this.message,name:this.name,description:this.description,number:this.number,fileName:this.fileName,lineNumber:this.lineNumber,columnNumber:this.columnNumber,stack:this.stack,config:this.config,code:this.code}},e}},function(e,t,n){"use strict";var r=n(2);e.exports=r.isStandardBrowserEnv()?function(){return{write:function(e,t,n,o,s,i){var a=[];a.push(e+"="+encodeURIComponent(t)),r.isNumber(n)&&a.push("expires="+new Date(n).toGMTString()),r.isString(o)&&a.push("path="+o),r.isString(s)&&a.push("domain="+s),i===!0&&a.push("secure"),document.cookie=a.join("; ")},read:function(e){var t=document.cookie.match(new RegExp("(^|;\\s*)("+e+")=([^;]*)"));return t?decodeURIComponent(t[3]):null},remove:function(e){this.write(e,"",Date.now()-864e5)}}}():function(){return{write:function(){},read:function(){return null},remove:function(){}}}()},function(e,t,n){"use strict";var r=n(18),o=n(19);e.exports=function(e,t){return e&&!r(t)?o(e,t):t}},function(e,t){"use strict";e.exports=function(e){return/^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)}},function(e,t){"use strict";e.exports=function(e,t){return t?e.replace(/\/+$/,"")+"/"+t.replace(/^\/+/,""):e}},function(e,t,n){"use strict";var r=n(2),o=["age","authorization","content-length","content-type","etag","expires","from","host","if-modified-since","if-unmodified-since","last-modified","location","max-forwards","proxy-authorization","referer","retry-after","user-agent"];e.exports=function(e){var t,n,s,i={};return e?(r.forEach(e.split("\n"),function(e){if(s=e.indexOf(":"),t=r.trim(e.substr(0,s)).toLowerCase(),n=r.trim(e.substr(s+1)),t){if(i[t]&&o.indexOf(t)>=0)return;"set-cookie"===t?i[t]=(i[t]?i[t]:[]).concat([n]):i[t]=i[t]?i[t]+", "+n:n}}),i):i}},function(e,t,n){"use strict";var r=n(2);e.exports=r.isStandardBrowserEnv()?function(){function e(e){var t=e;return n&&(o.setAttribute("href",t),t=o.href),o.setAttribute("href",t),{href:o.href,protocol:o.protocol?o.protocol.replace(/:$/,""):"",host:o.host,search:o.search?o.search.replace(/^\?/,""):"",hash:o.hash?o.hash.replace(/^#/,""):"",hostname:o.hostname,port:o.port,pathname:"/"===o.pathname.charAt(0)?o.pathname:"/"+o.pathname}}var t,n=/(msie|trident)/i.test(navigator.userAgent),o=document.createElement("a");return t=e(window.location.href),function(n){var o=r.isString(n)?e(n):n;return o.protocol===t.protocol&&o.host===t.host}}():function(){return function(){return!0}}()},function(e,t,n){"use strict";var r=n(2);e.exports=function(e,t){function n(e,t){return r.isPlainObject(e)&&r.isPlainObject(t)?r.merge(e,t):r.isPlainObject(t)?r.merge({},t):r.isArray(t)?t.slice():t}function o(o){r.isUndefined(t[o])?r.isUndefined(e[o])||(s[o]=n(void 0,e[o])):s[o]=n(e[o],t[o])}t=t||{};var s={},i=["url","method","data"],a=["headers","auth","proxy","params"],u=["baseURL","transformRequest","transformResponse","paramsSerializer","timeout","timeoutMessage","withCredentials","adapter","responseType","xsrfCookieName","xsrfHeaderName","onUploadProgress","onDownloadProgress","decompress","maxContentLength","maxBodyLength","maxRedirects","transport","httpAgent","httpsAgent","cancelToken","socketPath","responseEncoding"],c=["validateStatus"];r.forEach(i,function(e){r.isUndefined(t[e])||(s[e]=n(void 0,t[e]))}),r.forEach(a,o),r.forEach(u,function(o){r.isUndefined(t[o])?r.isUndefined(e[o])||(s[o]=n(void 0,e[o])):s[o]=n(void 0,t[o])}),r.forEach(c,function(r){r in t?s[r]=n(e[r],t[r]):r in e&&(s[r]=n(void 0,e[r]))});var f=i.concat(a).concat(u).concat(c),p=Object.keys(e).concat(Object.keys(t)).filter(function(e){return f.indexOf(e)===-1});return r.forEach(p,o),s}},function(e,t){"use strict";function n(e){this.message=e}n.prototype.toString=function(){return"Cancel"+(this.message?": "+this.message:"")},n.prototype.__CANCEL__=!0,e.exports=n},function(e,t,n){"use strict";function r(e){if("function"!=typeof e)throw new TypeError("executor must be a function.");var t;this.promise=new Promise(function(e){t=e});var n=this;e(function(e){n.reason||(n.reason=new o(e),t(n.reason))})}var o=n(23);r.prototype.throwIfRequested=function(){if(this.reason)throw this.reason},r.source=function(){var e,t=new r(function(t){e=t});return{token:t,cancel:e}},e.exports=r},function(e,t){"use strict";e.exports=function(e){return function(t){return e.apply(null,t)}}},function(e,t){"use strict";e.exports=function(e){return"object"==typeof e&&e.isAxiosError===!0}}])});
//# sourceMappingURL=axios.min.map
//=>./js/Slago.js
window.Slago = {
    //页面栈
    PageStack: {
        stack: [],
        push: function (div) {
            //如果Stack为空则更新scroll
            if(this.stack.length==0){
                window.Slago.ThreeIndexPage[window.Slago.ThreeIndexPage.lastPage].scroll=parseInt(window.pageYOffset);
            }
            //栈为空，则将Footer不显示
            window.Slago.Footer.none();
            //将上一个页面进行display:none
            if (this.stack.length > 0) {
                this.stack[this.stack.length - 1].scroll = parseInt(window.pageYOffset); //更新浮层顶页scroll
                if(this.stack[this.stack.length - 1].dom.setNone==undefined) {//跳入下一级钩子默认还要回来
                    this.stack[this.stack.length - 1].dom.style.display = "none";
                }else{
                    this.stack[this.stack.length - 1].dom.setNone();//调用跳入下一级钩子
                }
            }
            //入栈
            this.stack.push({
                dom: div,
                scroll: 0
            });
            //插入html
            document.getElementById("Slago.Containner").appendChild(div);
            //是否显示Containner
            if (this.stack.length > 0) {
                document.getElementById("Slago.Containner").style.display = "block";
                //关闭其他页面
                window.Slago.ThreeIndexPage.$closePage();
            }
        },
        pop: function () {
            /*页面返回上一级
             *返回值，上一级还有页面则返回true，否则返回false
             */
            if (this.stack.length > 0) {
                //向右划动动画效果
                window.Slago.PageSwitchAnimation.linearRight();
            } else { //关闭Containner
                this.clear();
                return false;
            }
        },
        clear: function () {
            //将containner全部页面删除
            while (window.Slago.PageStack.stack.length > 0) {
                window.Slago.PageStack.pop();
            }
            //关闭Containner
            document.getElementById("Slago.Containner").style.display = "none";
            //TO lastPage 注：即回到我们最后停留过的主页面去
            window.Slago.ThreeIndexPage.To(window.Slago.ThreeIndexPage.lastPage);
            //显示Footer ：将底部的docker选择栏显示出来
            window.Slago.Footer.block();
        },
        //删除倒数第二个结点 用途 用于返回刷新钩子
        deleteTwoLast:function(){
            if(this.stack.length>=2) {
                this.stack[this.stack.length - 2].dom.parentNode.removeChild(
                    this.stack[this.stack.length - 2].dom);
                this.stack.splice(this.stack.length - 2, 1);
            }
        },
        deleteDOMNode:function(domnode){
            let index=-1;
            for(let i=0;i<this.stack.length;i++){
                if(this.stack[i].dom.hash==domnode.hash){
                    index=i;
                    break;
                }
            }
            //删除 从dom树上去掉并从栈上删除
            if(index!=-1) {
                this.stack[index].dom.parentNode.removeChild(
                    this.stack[index].dom);
                this.stack.splice(index, 1);
            }
        }
    }, //End-PageStack
    //向页面栈创建新页面
    CreatePage: function (newnode) {
        let div = document.createElement("div");
        div.style.width = "640px";
        div.style.backgroundColor = "rgba(240, 248, 255, 0)";
        div.style.display = "block";
        div.style.marginLeft = "0px"; //设计页面贴换动画，必须设置为0px
        div.innerHTML = newnode;
        this.PageStack.push(div);
        return div;
    }, //End-CreatePage

    //四个主界面
    ThreeIndexPage: {
        AboutPage: {
            dom: document.getElementById("Slago.AboutPage"),
            scroll: 0
        },
        FindPage: {
            dom: document.getElementById("Slago.FindPage"),
            scroll: 0
        },
        UserPage: {
            dom: document.getElementById("Slago.UserPage"),
            scroll: 0
        },
        Containner: {
            dom: document.getElementById("Slago.Containner"),
            scroll: 0
        },
        lastPage: "FindPage", //web进入默认页面,lastPage并不存储Containner,lastPage为了从Containner到其他三个容器的过渡
        To: function (Index) {
            this.$closePage();
            if (Index != "Containner") {
                /*检测是否有页面是否有返回钩子，如果有则调用*/
                if(this[Index].dom.setBlock!=undefined){
                    this[Index].dom.setBlock();
                }else {
                    this[Index].dom.style.display = "block";
                }
                this.lastPage = Index;
                //更新页面滑动位置
                window.scrollTo(0, this[this.lastPage].scroll);
                //注意:此处设计To 与 window.Slago.PageStack.clear的递归
                if (window.Slago.PageStack.stack.length > 0) {
                    window.Slago.PageStack.clear();
                }
            }
        },
        $closePage: function () { //关闭现在页面并更新scroll等信息
            //更新scroll
            this[this.lastPage].dom.style.display = "none";
        }
    }, //End-ThreeIndexPage

    //加载悬浮页
    LoadPage:{
        hover:function(){
            this.move();
            //直接推进containner，以fixed形式呈现
            let template=""+
            '<!-- 单个资料信息设置页 -->'+
            '<div style="width:640px;height:100%;background-color: #ffffff00;display: flex;justify-content: center;align-items: center;">'+
            '    <img src="./img/load.gif" style="width:250px;height:200px;display:block;">'+
            '    <img src="./img/ok-08.png" style="width:100px;height:100px;display: none;">'+
            '</div>';
            //创建一个width:640 height：screen.height的div
            let pageNode=document.createElement("div");
            pageNode.style.width="640px";
            pageNode.style.height=window.screen.availHeight.toString()+"px";
            pageNode.style.backgroundColor="#ffffff00";
            pageNode.style.position="fixed";
            pageNode.style.top="0px";
            pageNode.style.zIndex="9999";
            pageNode.id="Slago.LoadHover";
            pageNode.innerHTML=template;
            //添加孩子节点
            document.getElementById("Slago.UI").children[0].appendChild(pageNode);
        },
        move:function(){
            let LoadNode=document.getElementById("Slago.LoadHover");
            if(LoadNode){
                LoadNode.parentNode.removeChild(LoadNode);
            }
        },
        trans:function(){
            let LoadNode=document.getElementById("Slago.LoadHover");
            if(LoadNode){
                LoadNode.children[0].children[0].style.display="none";
                LoadNode.children[0].children[1].style.display="block";
            }
            setTimeout("Slago.LoadPage.move();",500);
        }
    },

    //框架初始化
    Init: function () {
        //为Footer三个按钮绑定事件
        let FooterIcons = document.getElementsByClassName("Slago.FooterIcon");
        FooterIcons[0].onclick = function () {
            window.Slago.ThreeIndexPage.To("AboutPage");
            document.getElementsByClassName("Slago.FooterIcon")[0].src = "./img/about_blue.png";
            document.getElementsByClassName("Slago.FooterIcon")[1].src = "./img/find_gray.png";
            document.getElementsByClassName("Slago.FooterIcon")[2].src = "./img/home_gray.png";
        };
        FooterIcons[1].onclick = function () {
            window.Slago.ThreeIndexPage.To("FindPage");
            document.getElementsByClassName("Slago.FooterIcon")[0].src = "./img/about_gray.png";
            document.getElementsByClassName("Slago.FooterIcon")[1].src = "./img/find_blue.png";
            document.getElementsByClassName("Slago.FooterIcon")[2].src = "./img/home_gray.png";
        };
        FooterIcons[2].onclick = function () {
            window.Slago.ThreeIndexPage.To("UserPage");
            document.getElementsByClassName("Slago.FooterIcon")[0].src = "./img/about_gray.png";
            document.getElementsByClassName("Slago.FooterIcon")[1].src = "./img/find_gray.png";
            document.getElementsByClassName("Slago.FooterIcon")[2].src = "./img/home_blue.png";
        };
        //劫持返回按键
        window.Slago.HijackReturnButton();
    },

    //控制body背景颜色
    backgroundColor:{
        seting(color){
            document.getElementById("Slago.UI").style.backgroundColor=color;
        },
        default(){
            document.getElementById("Slago.UI").style.color="ffffff";
        }
    },


    //footer显示控制
    Footer: {
        none: function () {
            document.getElementById("Slago.Footer").style.display = "none";
            document.getElementById("Slago.FooterBlankSpace").style.display = "none";
        },
        block: function () {
            document.getElementById("Slago.Footer").style.display = "flex";
            document.getElementById("Slago.FooterBlankSpace").style.display = "block";
        }
    },

    //页面切换动画
    PageSwitchAnimation: {
        //线性向右切动画，直接操纵Slago.PageStack
        linearRight: function () {
            let PageStack=Slago.PageStack;
            let nowLeft = parseInt(PageStack.stack[PageStack.stack.length - 1].dom.style.marginLeft);
            if (nowLeft >= 640) {
                //删除页面栈末尾节点
                PageStack.stack[PageStack.stack.length - 1].dom.parentNode.removeChild(PageStack.stack[PageStack.stack.length - 1].dom);
                PageStack.stack.pop(); //弹出数组最后一个元素
                //显示栈顶页面
                if (PageStack.stack.length > 0) {
                    if(PageStack.stack[PageStack.stack.length - 1].dom.setBlock!=undefined){
                        PageStack.stack[PageStack.stack.length - 1].dom.setBlock();//返回钩子
                    }else{
                        PageStack.stack[PageStack.stack.length - 1].dom.style.display = "block";
                    }
                    window.scrollTo(0, PageStack.stack[PageStack.stack.length - 1].scroll);
                    if(PageStack.stack.length==0){//栈为空
                        PageStack.clear();
                    }
                } else { //弹栈后栈为空，则关闭Containner显示其他页面
                    PageStack.clear();
                }
            } else {
                nowLeft+=40;
                //console.log(nowLeft);
                PageStack.stack[PageStack.stack.length - 1].dom.style.marginLeft = nowLeft.toString() + "px";
                //递归
                setTimeout('Slago.PageSwitchAnimation.linearRight()',4);
            }
        }
    },

    //返回按键劫持
    HijackReturnButton:function(){
        window.history.pushState({title:"title",url:"#"},"title","#");
        window.addEventListener("popstate",function(){
            //加载浮层消失
            window.stop();
            window.Slago.LoadPage.move();
            window.Slago.PageStack.pop();//返回上级
            //栈不为空
            if(window.Slago.PageStack.stack.length!=0){
                window.history.pushState({title:"title",url:"#"},"title","#");                
            }//否则应该退出本站了
        },false);
    },

    //全局变量解决方案
    Data:{
        ServerData:{
            IP:"http://127.0.0.1:12344/"
            //IP:"http://61.171.51.135:5555/"
        }
    },

    //Cookie相关操作工具
    CookieTool:{
        setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays*24*60*60*1000));
            var expires = "expires=" + d.toGMTString();
            document.cookie=cname+"="+cvalue+": " + expires;
        },
        getCookie(cname){
            var name = cname+"=";
            var ca = document.cookie.split(';');
            for(var i=0;i<ca.length;i++){
                var c=ca[i].trim();
                if(c.indexOf(name)===0) return c.substring(name.length, c.length);
            }
            return "";
        },
    }
};
//初始化
window.Slago.Init();
SlagoModel.FindPage={};

//=>./js/FindPage/Header.js
(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面Header-->',
    '<div style="width:640px;height:150px;position:fixed;background-color:#ffffff;border-bottom:1px rgb(77, 160, 255) solid;">',
    '    <div style="width:640px;height:14px;background-color:#ffffff;"></div>',
    '    <!-- 标题与搜索栏 -->',
    '    <div style="width:640px;height:50%;display: flex;flex-wrap: wrap;">',
    '        <div style="width:140px;background-color:rgb(255, 255, 255);height:100%;',
    '                    font-size:45px;font-weight:bold;display:flex;justify-content: center;',
    '                    align-items: center;color:#11121b;">发现</div>',
    '        <!-- 搜索栏 -->',
    '        <div style="width:500px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;flex-wrap: wrap;">',
    '            <input type="text" style="width:350px;height:50px;margin-left: 50px;outline:none;text-align: center;',
    '            border-radius: 25px;background-color: rgb(193, 227, 255);font-size: 27px;">',
    '            <img src="./img/搜索.png" class="hoverPointer" style="height:50px;border-top-right-radius: 25px;border-bottom-right-radius:25px ;margin-left: 10px;">',
    '        </div>',
    '    </div>',
    '    <!-- 页面内选择栏 -->',
    '    <div style="width:640px;height:40%;background-color: rgb(255, 255, 255);">',
    '        <!-- 字体栏 -->',
    '        <div style="width:640px;height:80%;background-color: #ffffff;">',
    '            <div style="font-size: 25px;height:100%;color:#0066cc;display: flex;align-items: center;',
    '            margin-left: 23px;">精选</div>',
    '        </div>',
    '        <!-- 滑动条栏 -->',
    '        <div style="width:640px;height:10px;background-color: #ffffff;">',
    '            <div style="width:50px;height:6px;background-color: #0066cc;',
    '            border-radius:3px;margin-left: 24px;"></div>',
    '        </div>',
    '    </div>',
    '</div>',
    '<!--空白-->',
    '<div style="height:150px;width:640px;"></div>'
    ].join("");
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,{});
    };
    //加入模块
    SlagoModel.FindPage.Header=namespace;
})();
//=>./js/FindPage/post_model.js
//=>post_model.js=>start
(function(){
    //建立命名空间
    let namespace={};
    //帖子模块模板,瀑布流组件
    namespace.mediaStream=[
   '<!--基本容器-->',
   '<div style="width:100%;background-color:rgb((202, 248, 204))">',
   '    <!--作者信息栏-->',
   '    <div style="width:100%;height:80px;background-color:rgb(255,255, 255);display:flex;flex-wrap:wrap;">',
   '        <!--头像框-->',
   '        <div style=" width:70px;height:70px;background-color: rgb(255, 255, 255);margin-left: 15px;border-radius:40px;margin-top: 5px;">',
   '            <!--头像-->',
   '            <img src="{{UserHeadPic}}" style="width:100%;height:100%;border-radius:50%;">',
   '        </div>',
   '        <!--昵称栏-->',
   '        <div style="width:445px;height:80px;background-color:rgb(255, 255, 255);display: flex;align-items: center;margin-left: 10px;">',
   '            <!--昵称文字-->',
   '            <span style="color: #585858;font-size:26px;font-weight: bold;">{{Username}}</span>',
   '        </div>',
   '        <!--关注按钮-->',
   '        <div style="width:70px;height:80px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
   '            <!--是否关注文字-->',
   '            <span style="color:rgb(247, 122, 122);font-size: 24px;">{{aboutlike}}</span>',
   '        </div>',
   '    </div>',
   '    <!--九宫格图片-->',
   '    <div style="width:100%;">',
   '        <!--行-->',
   '        {{#Image}}',
   '            <div style="width:640px;height:230px;background-color: rgb(255, 255, 255);display: flex;justify-content: space-around;align-items: center;">',
   '                <!--图片容器-->',
   '                {{#ImageList}}',
   '                    <div style="width:200px;height:200px;overflow: hidden;display: flex;align-items: center;border-radius: 10px;">',
   '                        <img src="{{.}}" style="width:100%;border-radius:10px;" onclick="IMageClick()" >',
   '                    </div>',
   '                {{/ImageList}}',
   '            </div>',
   '        {{/Image}}',
   '    </div>',
   '    <!--交互栏-->',
   '    <div style="width:100%;height:70px;background-color:rgb(255, 255, 255);display: flex;align-items: center;flex-wrap: wrap;">',
   '        <!--点赞按钮-->',
   '        <div style="width:50px;height:50px;background-color: #ffffff;margin-left: 25px;">',
   '            <img src="{{likePic}}" style="width:100%;height:100%;">',
   '        </div>',
   '        <!--评论按钮-->',
   '        <div style="width:50px;height:50px;background-color:#ffffff;margin-left:40px;">',
   '            <img src="{{messagePic}}" style="width:100%;height:100%;">',
   '        </div>',
   '        <!--收藏按钮-->',
   '        <div style="width:44px;height:44px;background-color: #ffffff;margin-left: 40px;">',
   '            <img src="{{starPic}}" style="width:100%;height:100%;">',
   '        </div>',
   '        <!--三个点-->',
   '        <div style="width:50px;height:50px;background-color: #ffffff;margin-left: 330px;">',
   '            <img src="{{threedotPic}}" style="width:100%;height:100%;">',
   '        </div>',
   '    </div>',
   '</div>',
    ].join("");
    namespace.data={
        UserHeadPic:"https://weiliicimg9.pstatp.com/weili/l/907007723288002647.webp",
        likePic:"./img/heart_gray.png",
        messagePic:"./img/消 息.png",
        threedotPic:"./img/三个点.png",
        starPic:"./img/收 藏.png",
        Username:"高万禄",
        aboutlike:"关注",
        Image:[
            {
                ImageList:[
                    "https://icweiliimg1.pstatp.com/weili/l/903716068942282770.webp",
                    "https://weiliicimg9.pstatp.com/weili/l/919963990356394030.webp",
                    "https://weiliicimg6.pstatp.com/weili/l/920147325325672571.webp",
                ]
            },
            {
                ImageList:[
                    "https://icweiliimg1.pstatp.com/weili/l/921007487020695583.webp",
                    "https://weiliicimg1.pstatp.com/weili/l/919857475167846458.webp",
                    "http://119.3.180.71/DataBase/123/img/55.jpg"
                ]
            },
        ]
    };
    namespace.getModel=function(){
       //console.log(Slagolib.template.engin(this.mediaStream,this.data));
       return Slagolib.template.engin(this.mediaStream,this.data)+Slagolib.template.engin(this.mediaStream,this.data);
    };
    //加入模块
    SlagoModel.FindPage.post_model=namespace;
})();
//=>post_model.js=>end
//=>./js/FindPage/findPage.js
(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面容器-->',
    '<div style="width:640px;background-color:#ffffff;">',
    '    <!--页面Header-->',
    '    {{HeaderTemplate}}',
    '    <!--帖子流-->',
    '    {{PostStream}}',
    '</div>',
    ].join("");
    namespace.data={
        HeaderTemplate:SlagoModel.FindPage.Header.getModel(),//获得导航栏
        PostStream:SlagoModel.FindPage.post_model.getModel(),//获得帖子流
    }
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,this.data);
    };
    //加入模块
    SlagoModel.FindPage.findPage=namespace;
})();
SlagoModel.PostSuspensionPage={};

//=>./js/PostSuspensionPage/postPage.js
(function(){
    //建立命名空间
    let namespace={};
    //帖子悬浮页
    namespace.Page=[
    '<div style="width:640px;background-color: rgb(255, 255, 255);">',
    '<!-- 导航栏 -->',
    '<div style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(240, 240, 240);">',
    '    <!-- 返回按键 -->',
    '    <div class="hoverPointer" onclick="Slago.PageStack.pop()" style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
    '        <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
    '        <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
    '            <span style="font-size: 27px;color:#0066cc;">返回</span>',
    '        </div>',
    '    </div>',
    '    <!-- 点赞按钮 -->',
    '    <div style="height:100%;display: flex;align-items: center;margin-left: 330px;">',
    '        <img src="./img/heart_gray.png" style="width:50px;height:50px;">',
    '    </div>',
    '    <!-- 用户头像 -->',
    '    <div style="height:100%;display: flex;align-items: center;">',
    '        <img src="https://weiliicimg9.pstatp.com/weili/l/907007723288002647.webp" ',
    '        style="width:60px;height:60px;border-radius: 30px;margin-left: 60px;"> ',
    '    </div>',
    '</div>',
    '<div style="height:80px;"></div>',
    '<!-- 内容主题 -->',
    '<div style="width:640px;background-color: #ffffff;">',
    '    <!-- 图片瀑布 -->',
    '    <img src="https://icweiliimg1.pstatp.com/weili/l/903716068942282770.webp" style="width:100%;">',
    '    <img src="https://weiliicimg9.pstatp.com/weili/l/919963990356394030.webp" style="width:100%;">',
    '    <img src="https://weiliicimg6.pstatp.com/weili/l/920147325325672571.webp" style="width:100%;">',
    '    <img src="https://icweiliimg1.pstatp.com/weili/l/921007487020695583.webp" style="width:100%;">',
    '    <img src="https://weiliicimg1.pstatp.com/weili/l/919857475167846458.webp" style="width:100%;">',
    '</div>',
    '<!-- 发帖日期 -->',
    '<div style="width:100%;height:30px;background-color: rgb(255, 255, 255);display: flex;align-items: center;border-bottom: 1px #e6e6e6 solid;">',
    '    <span style="height:100%;font-size: 18px;color:#525252;margin-left: 20px;display: flex;align-items: center;">0&nbsp喜欢</span>',
    '    <span style="height:100%;font-size: 18px;color:#525252;margin-left: 400px;display: flex;align-items: center;">2020年12月21日</span>',
    '</div>',
    '<!-- 用户名及描述栏 -->',
    '<div style="width:640px;background-color: #ffffff;display: flex;flex-wrap: wrap;border-bottom: 1px #e6e6e6 solid;">',
    '    <div style="width:100%;display: flex;align-items: center;padding: 10px;flex-wrap: wrap;font-size: 21px;">',
    '        <a href="#" style="text-decoration: none;">高万禄:</a>',
    '        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp差别比尔别本次恶补比不鄂毕本次，热比八不欸金额不考虑北京河北不承认可比口径',
    '        地方发动机财务科保存文件就不，曾看见外部接口编辑出版科比尽快。',
    '    </div>',
    '</div>',
    '<!-- 评论系统 -->',
    '<div style="width:640px;height:200px;background-color: #ffffff;">',
    '    ',
    '</div>',
    '</div>',
    ].join("");
    namespace.getModel=function(){
        //console.log(Slagolib.template.engin(this.Page,{}));
        return Slagolib.template.engin(this.Page,{});
    }
    //加入模块
    SlagoModel.PostSuspensionPage.postPage=namespace;
})();
SlagoModel.UserPersonal={};

//=>./js/UserPersonal/PersonalPageOptionsComponent.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '<!-- 用户主页选项栏 -->',
        '<div style="width:640px;height:500px;background-color: rgb(255, 255, 255);">',
        '    <!-- 个人资料设置 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 30px;display: flex;',
        '    border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.UserData.userDataPage.show();">',
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/展示信息设置.png" style="height:50%;margin-left: 30px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            个人信息',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 帖子 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 30px;display: flex;',
        '    border-top: 1px solid #dfdfdf;border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.PersonalPostPage.show()">',//点击显示帖子页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-picture.png" style="height:42%;margin-left: 34px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            帖子',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 成就 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 10px;display: flex;',
        '    border-bottom:1px solid #dfdfdf ;" onclick="SlagoModel.UserPersonal.PersonalAchievementPage.show()">',//点击显示个人成就页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-planet.png" style="height:40%;margin-left: 30px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            成就',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '    <!-- 更多 -->',
        '    <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;margin-top: 10px;display: flex;" onclick="SlagoModel.UserPersonal.MorePage.show()">',//点击显示更多页面
        '        <!-- icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);display: flex;align-items: center;">',
        '            <img src="./img/ts-star-2.png" style="height:45%;margin-left: 33px;">',
        '        </div>',
        '        <!-- 字体 -->',
        '        <div style="width:200px;background-color: rgb(255, 255, 255);',
        '        display: flex;align-items:center;font-size: 26px;font-weight:700;color: rgb(68, 68, 68);',
        '        margin-left: 20px;',
        '        ">',
        '            更多',
        '        </div>',
        '        <!-- 右箭头icon -->',
        '        <div style="width:120px;height:100%;background-color: rgb(255, 255, 255);margin-left: 175px;">',
        '            <img src="./img/箭头_右.png" style="height:50%;margin-top: 20%;margin-left: 60px;">',
        '        </div>',
        '    </div>',
        '</div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    //加入模块
    SlagoModel.UserPersonal.PersonalPageOptionsComponent=namespace;
})();
//=>./js/UserPersonal/my_page.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=`
    <!-- 用户个人页面 -->
    <div style="width:640px;background-color: rgb(255, 255, 255);">
        <!-- 用户信息块\昵称\关注\粉丝\头像等元素 -->
        <div style="width:620px;
                    height:200px;margin-left: 10px;
                    display: flex;flex-wrap: wrap;justify-content: space-around;
                    align-items: center;margin-top: 30px;
                    ">
            <!-- 头像 -->
            <div style="width:140px;height:140px;
                        background-color:rgb(255, 255, 255);
                        border-radius:80px;">
                        <!--点击头像进入个人信息页面-->
                <img onclick="SlagoModel.UserPersonal.UserData.userDataPage.show();" src="{{userHeadImgURL}}"
                    style="width:100%;height:100%;border-radius:50%;">
            </div>
            <!-- 个人成就 -->
            <div style="width:440px;height:140px;">
                <!-- 用户名 -->
                <div style="width:100%;
                            height:70px;
                            background-color:rgb(255, 255, 255);
                            display:flex;
                            justify-content:center;
                            align-items:center;
                            font-size: 27px;
                            color:rgb(68,68,68);">
                    <p>{{UserName}}</p>
                </div>
                <!-- 成就栏 -->
                <div style="width:100%;
                            height:70px;
                            background-color:rgb(255, 255, 255);
                            display: flex;
                            justify-content:center;
                            align-items: center;
                            font-size: 24px;
                            color:rgb(68,68,68);">
                    <p>{{AboutNum}} 关注 {{FansNum}} 粉丝 {{LikeNum}} 喜欢</p>
                </div>
            </div>
        </div>
    {{PersonalPageOptionsComponent}}
    </div>`;

    namespace.getModel=function(DOMData){
        return Slagolib.template.engin(this.Page,DOMData);
    }
    namespace.show=function(){
        let PAGENODE=document.getElementById("Slago.UserPage");
        let DOMData={
            //获取选择模块
            PersonalPageOptionsComponent:SlagoModel.UserPersonal.PersonalPageOptionsComponent.getModel(),
            //头像地址
            userHeadImgURL:Slago.Data.ServerData.IP+"getUserHeadImg?id="+
                Slago.CookieTool.getCookie("id")+"&random="+Math.random().toString(),
            UserName:"loading...",//昵称
            //喜欢数量
            LikeNum:"_",
            //粉丝数量
            FansNum:"_",
            //关注数量
            AboutNum:"_"
        };
        /*为个人页设置返回钩子，以便刷新最新个人信息
        * */
        PAGENODE.setBlock=function(){
            PAGENODE.style.display="block";
            namespace.show();
            // console.log("重新刷新个人页内容");
        }
        PAGENODE.innerHTML=this.getModel(DOMData);
        //@AJAX 发起GET请求获取用户昵称
        axios.get(Slago.Data.ServerData.IP+
            "getUserName").then(response=>{
            DOMData.UserName=response.data;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
        //@AJAX 发起请求获得用户 关注 喜欢 收藏数量
        axios.get(Slago.Data.ServerData.IP+
            "getLikeAboutFans?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.LikeNum=response.data.likeNum;
            DOMData.AboutNum=response.data.aboutNum;
            DOMData.FansNum=response.data.fansNum;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
    }
    //加入模块
    SlagoModel.UserPersonal.my_page=namespace;
})();
//=>./js/UserPersonal/PersonalPostPage.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '<!-- 个人帖子页组件 -->',
        '<div style="width:640px;background-color: #ffffff;height:500px;">',
        '    <!-- 导航栏 -->',
        '    <div',
        '        style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '        <!-- 返回按键 -->',
        '        <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '            style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '            <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '            <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '            </div>',
        '        </div>',
        '        <!--导航栏字体栏-->',
        '        <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '           帖子',
        '        </div>',
        '    </div>',
        '    <div style="height:80px;"></div>',
        '</div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    namespace.show=function(){
        //创建页面,推入页面栈
        Slago.CreatePage(this.getModel());
    }
    //加入模块
    SlagoModel.UserPersonal.PersonalPostPage=namespace;
})();
//=>./js/UserPersonal/PersonalAchievementPage.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '<!-- 个人成就页组件 -->',
        '<div style="width:640px;background-color: #ffffff;height:500px;">',
        '    <!-- 导航栏 -->',
        '    <div',
        '        style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '        <!-- 返回按键 -->',
        '        <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '            style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '            <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '            <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '            </div>',
        '        </div>',
        '        <!--导航栏字体栏-->',
        '        <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '           成就',
        '        </div>',
        '    </div>',
        '    <div style="height:80px;"></div>',
        '</div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    namespace.show=function(){
        //创建页面,推入页面栈
        Slago.CreatePage(this.getModel());
    }
    //加入模块
    SlagoModel.UserPersonal.PersonalAchievementPage=namespace;
})();
//=>./js/UserPersonal/MorePage.js
//=>UserPersonal.UserData.MorePage=>start
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
    '    <!-- 更多页面 -->',
    '    <div style="width:640px;background-image: linear-gradient(rgb(0, 0, 0), rgb(14, 11, 11));">',
    '    <!-- 导航栏 -->',
    '    <div',
    '        style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
    '        <!-- 返回按键 -->',
    '        <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
    '            style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
    '            <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
    '            <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
    '                <span style="font-size: 27px;color:#0066cc;">返回</span>',
    '            </div>',
    '        </div>',
    '        <!--导航栏字体栏-->',
    '        <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
    '           更多',
    '        </div>',
    '    </div>',
    '    <div style="height:80px;"></div>',
    '    <!-- 星空图片 -->',
    '    <img src="./img/source/夜空.png" style="width:640px;">',
    '    <div style="width: 640px;height:150px;color:#ffffff;font-size: 50px;font-weight: lighter;',
    '         display: flex;justify-content: center;align-items: center;">',
    '            图享',
    '    </div>',
    '         <div style="width:640px;display: flex;justify-content: center;',
    '         font-size: 25px;color: #ffffff;flex-wrap: wrap;">',
    '            <div style="width:100%;display: flex;justify-content: center;">分享生活乐趣</div>',
    '            <ul style="margin-top: 50px;">',
    '                <li>版权:<a href="https://github.com/gaowanlu/Slago" style="color:#fafafa;" target="_blank">GitHub开源项目</a></li>',
    '                <li style="margin-top: 20px;">关于我们:图享起始于2021年个人开源前端项目</li>',
    '                <li style="margin-top: 20px;">开发者:高万禄</li>',
    '                <li style="margin-top: 20px;">联系我们:heizuboriyo@gmail</li>',
    '                <li style="margin-top: 20px;">地址:桂林电子科技大学(花江校区)</li>',
    '                <li style="margin-top: 20px;">版本:v0.0.1</li>',
    '            </ul>',
    '         </div>',
    '         <!--占位div-->',
    '         <div style="height:{{screenHeight}}px;width:100%;">',
    '           <img src="./img/source/自行车.png" style="width:100%;">',
    '         </div>',
    '    </div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{
            screenHeight:window.screen.height.toString()
        });
    }
    namespace.show=function(){
        //创建页面,推入页面栈
        Slago.CreatePage(this.getModel());
    }
    //加入模块
    SlagoModel.UserPersonal.MorePage=namespace;
})();
//=>UserPersonal.UserData.MorePage=>end
SlagoModel.UserPersonal.UserData={};//个人信息

//=>./js/UserPersonal/UserData/userDataPage.js
//=>SlagoModel.UserPersonal.UserData.userDataPage=>start
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
    '    <!-- 个人资料页 -->',
    '    <div style="width:640px;background-color: rgb(255, 255, 255);">',
    '       <!-- 导航栏 -->',
    '       <div',
    '           style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
    '           <!-- 返回按键 -->',
    '           <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
    '               style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
    '               <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
    '               <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
    '                   <span style="font-size: 27px;color:#0066cc;">返回</span>',
    '               </div>',
    '           </div>',
    '           <!--导航栏字体栏-->',
    '           <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
    '           个人信息',
    '           </div>',
    '       </div>',
    '       <div style="height:80px;width:100%;"></div>',
    '        <!-- 头像栏 -->',
    '        <div class="hoverPointer" onclick="SlagoModel.UserPersonal.UserData.userDataPage.HeadImgSeting(this)" style="width:640px;height:130px;background-color:#ffffff ;display: flex;">',
    '            <!-- 字体提示栏 -->',
    '            <div style="width:150px;height:130px;background-color: rgb(255, 255, 255);',
    '            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                头像',
    '            </div>',
    '            <!-- 主题内容 -->',
    '            <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                <!-- 头像图片 -->',
    '                <img style="width:100px;height:100px;border-radius: 5px;margin-top: 15px;margin-left: 315px;" src="{{headImgURL}}">',
    '            </div>',
    '            <!-- 右箭头 -->',
    '            <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                <img src="./img/箭头_右.png" style="width:40px;">',
    '            </div>',
    '            <input type="file" accept="image/*" style="display: none;">',
    '        </div>',

    '        <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '        <!-- 昵称栏 -->',
    '        <div onclick="SlagoModel.UserPersonal.UserData.nameSetingPage.show()" class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '            <!-- 字体提示栏 -->',
    '            <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                昵称',
    '            </div>',
    '            <!-- 主题内容 -->',
    '            <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                <div style="height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                    {{name}}',
    '                </div>',
    '            </div>',
    '            <!-- 右箭头 -->',
    '            <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                <img src="./img/箭头_右.png" style="width:40px;">',
    '            </div>',
    '        </div>',
    '        <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '        <!-- 账号栏 -->',
    '        <div class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '            <!-- 字体提示栏 -->',
    '            <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                图享号',
    '            </div>',
    '            <!-- 主题内容 -->',
    '            <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                <div style="height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                    {{id}}',
    '                </div>',
    '            </div>',
    '            <!-- 右箭头 -->',
    '            <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                <img src="./img/箭头_右.png" style="width:40px;">',
    '            </div>',
    '        </div>',
    '        <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '        <!-- 性别 -->',
    '        <div class="hoverPointer" onclick="SlagoModel.UserPersonal.UserData.sexSetingPage.show()" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '            <!-- 字体提示栏 -->',
    '            <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                性别',
    '            </div>',
    '            <!-- 主题内容 -->',
    '            <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                <div style="height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                    {{sex}}',
    '                </div>',
    '            </div>',
    '            <!-- 右箭头 -->',
    '            <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                <img src="./img/箭头_右.png" style="width:40px;">',
    '            </div>',
    '        </div>',
    '        <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '        <!-- 个性签名 -->',
    '        <div class="hoverPointer" onclick="SlagoModel.UserPersonal.UserData.signature.show()" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '            <!-- 字体提示栏 -->',
    '            <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                个性签名',
    '            </div>',
    '            <!-- 主题内容 -->',
    '            <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                <div style="height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                    {{profile}}',
    '                </div>',
    '            </div>',
    '            <!-- 右箭头 -->',
    '            <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                <img src="./img/箭头_右.png" style="width:40px;">',
    '            </div>',
    '        </div>',
    '    </div>',
    ].join("");

    //头像设置
    namespace.HeadImgSeting=function(dom){
        //创建表单对象，并加入文件对象
        let formFile = new FormData();
        let input=dom.children[dom.children.length-1];
        //@AJAX上串头像表单
        let submitForm=function(form){
            axios.request({
                url:Slago.Data.ServerData.IP+"setHeadImg",
                method:'post',
                header:{'Content-Type': 'multipart/form-data'},
                data:form
            }).then(response=>{
                if(response.data.result=="true")
                    Slago.LoadPage.trans();
                setTimeout("Slago.LoadPage.move();",500);
            }).catch(error=>{
                Slago.LoadPage.move();
            })
        }
        //input添加状态改变事件
        input.onchange=function(){
            //获得input下面的头像img节点
            let imgNode=this.parentNode.children[1].children[0];
            //检测图像文件是否选择
            if(this.files!=undefined&&this.files.length>0&&this.files&&this.files[0]){
                if(this.files[0].getAsDataURL){
                    imgNode.src=this.files[0].getAsDataURL;
                    Slago.LoadPage.hover();//进行悬浮层
                }else{
                    imgNode.src=window.URL.createObjectURL(this.files[0]);
                    Slago.LoadPage.hover();//进行悬浮层
                }
                formFile.append("headImg", this.files[0]); //加入文件对象
                submitForm(formFile);
            }else if(input_file.value){
                imgNode.src=input_file.value;
                Slago.LoadPage.hover();//进行悬浮层
                formFile.append("headImg", input_file); //加入文件对象
                submitForm(formFile);
            }
        }
        //点击表单
        input.click();
    };
    namespace.getModel = function (DOMData) {
        return Slagolib.template.engin(this.Page, DOMData);
    }
    namespace.show = function () {
        //数据
        let DOMData={
            name:"",//昵称
            id:Slago.CookieTool.getCookie("id"),//从浏览器cookie获得账号
            sex:"",//性别
            profile:"",//个性签名
            headImgURL:Slago.Data.ServerData.IP+
                "getUserHeadImg?id="+ Slago.CookieTool.getCookie("id")+
                "&random="+Math.random().toString()
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //返回钩子
        //返回钩子(BACK HOOK)
        PAGENODE.setBlock=function(){
            //-----FINAL
            namespace.show();//另起页面
            Slago.PageStack.deleteTwoLast();//干掉原来的此页的老页面
            //-----FINAL
        };
        //@AJAX 发起GET请求获取用户昵称
        axios.get(Slago.Data.ServerData.IP+
            "getUserName").then(response=>{
            DOMData.name=response.data;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] userDataPage.js");
        });
        //@AJAX 发起get请求获取用户性别
        axios.get(Slago.Data.ServerData.IP+
            "getUserSex?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.sex=response.data.result;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求性别信息失败] userDataPage.js");
        });
        //@AJAX 发起get请求获取用户个性签名
        axios.get(Slago.Data.ServerData.IP+
            "getUserProfile?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.profile =response.data.result;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求个性签名信息失败] userDataPage.js");
        });
    }
    //加入模块
    SlagoModel.UserPersonal.UserData.userDataPage=namespace;
})();
//=>SlagoModel.UserPersonal.UserData.userDataPage=>end
//=>./js/UserPersonal/UserData/nameSetingPage.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
    '    <!-- 单个资料信息设置页 -->',
    '    <div style="width:640px;height:600px;background-color: #ffffff;">',
    '        <!-- 导航栏 -->',
    '        <div',
    '            style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
    '            <!-- 返回按键 -->',
    '            <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
    '                style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
    '                <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
    '                <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
    '                    <span style="font-size: 27px;color:#0066cc;">返回</span>',
    '                </div>',
    '            </div>',
    '            <!--导航栏字体栏-->',
    '            <div',
    '                style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
    '                昵称',
    '            </div>',
    '        </div>',
    '        <div style="height:80px;width:100%;"></div>',
    '        <!-- 昵称设置栏 -->',
    '                <!-- 昵称栏 -->',
    '                <div onclick="SlagoModel.UserPersonal.UserData.nameSetingPage.inputClick(this)" class="hoverPointer" style="width:640px;height:100px;background-color: #ffffff;display: flex;">',
    '                    <!-- 字体提示栏 -->',
    '                    <div style="width:150px;height:100px;background-color: rgb(255, 255, 255);',
    '                    display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
    '                        昵称',
    '                    </div>',
    '                    <!-- 主题内容 -->',
    '                    <div style="width:420px;height:100%;background-color: rgb(255, 255, 255);">',
    '                        <div style="background-color:rgb(255, 255, 255);height:100%;width:400px;margin-left: 15px;display: flex;align-items: center;justify-content: flex-end;font-size: 20px;color:#707070;overflow: hidden;">',
    '                            <!-- 输入框 -->',
    '                            <input id="SlagoModel.UserPersonal.UserData.nameSetingPage.inputnode" type="text" style="width:100%;height:80%;',
    '                            outline: none;text-align: right;font-size: 25px;color:#1f1f1f;',
    '                            caret-color: #0066cc;" value="{{name}}">',
    '                        </div>',
    '                    </div>',
    '                    <!-- 右箭头 -->',
    '                    <div style="height: 100%;width:40px;background-color: rgb(255, 255, 255);display: flex;justify-content: center;align-items: center;">',
    '                        <img src="./img/箭头_右.png" style="width:40px;">',
    '                    </div>',
    '                </div>',
    '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
    '            <!-- 保存按钮栏 -->',
    '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
    '               <div class="hoverPointer" onclick="SlagoModel.UserPersonal.UserData.nameSetingPage.submit(this)" style="width:120px;height:60px;border-radius:30px;',
    '                            background-color: #0066cc;color: #ffffff;display: flex;',
    '                            justify-content: center;align-items: center;font-size: 24px;margin-left: 505px;">',
    '               保存',
    '               </div>',
    '            </div>',
    '    </div>',
    ].join("");
    /*点击输入框水平位置出发点击输入框，增加覆盖范围 */
    namespace.inputClick=function(dom){
        let input=dom.children[1].children[0].children[0];
        input.click();
    }
    namespace.getModel=function(obj){
        return Slagolib.template.engin(this.Page,obj);
    }
    namespace.show=function(){
        //数据
        let DOMData={
            name:"",
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起GET请求获取用户昵称
        axios.get(Slago.Data.ServerData.IP+
            "getUserName").then(response=>{
                DOMData.name=response.data;
                //重新渲染页面
                PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求昵称信息失败] nameSetingPage.js");
        });
    }
    //点击保存按钮
    namespace.submit=function(dom){
        //获取输入框node
        let input=document.getElementById("SlagoModel.UserPersonal.UserData.nameSetingPage.inputnode");
        Slago.LoadPage.hover();
        axios.get(Slago.Data.ServerData.IP+
            "updateUserName?"+
            "newname="+input.value).then(function(response){
            setTimeout("Slago.LoadPage.trans();",300);
        }).catch(error=>{
            Slago.LoadPage.move();//失败则去掉加载页
        })
    }
    //加入模块
    SlagoModel.UserPersonal.UserData.nameSetingPage=namespace;
})();
//=>./js/UserPersonal/UserData/sexSetingPage.js
//=>SlagoModel.UserPersonal.UserData.sexSetingPage=>start
(function () {
    //建立命名空间
    let namespace = {};
    //渲染页
    namespace.Page = [
        '    <!-- 性别设置界面 -->',
        '    <div style="width:640px;background-color: rgb(255, 255, 255);">',
        '        <!-- 单个资料信息设置页 -->',
        '        <div style="width:640px;background-color: #ffffff;">',
        '            <!-- 导航栏 -->',
        '            <div',
        '                style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '                <!-- 返回按键 -->',
        '                <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '                    style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '                    <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '                    <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                        <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '                    </div>',
        '                </div>',
        '                <!--导航栏字体栏-->',
        '                <div',
        '                    style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '                    性别',
        '                </div>',
        '            </div>',
        '            <div style="height:80px;width:100%;"></div>',
        '            <!-- 性别设置栏 -->',
        '',
        '',
        '            <!-- 男 -->',
        '            <div id="SlagoModel.UserPersonal.UserData.sexSetingPage.man" ',
        'onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)" class= "hoverPointer"',
        '                style="width:640px;height:100px;background-color: {{men}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    男',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 女 -->',
        '            <div  class="hoverPointer" id="SlagoModel.UserPersonal.UserData.sexSetingPage.woman" ',
        '   onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)"          ',
        '   style="width:640px;height:100px;background-color: {{women}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    女',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 其他 -->',
        '            <div  class="hoverPointer"',
        ' id="SlagoModel.UserPersonal.UserData.sexSetingPage.other" onclick = "SlagoModel.UserPersonal.UserData.sexSetingPage.choose(this)" ',
        '                style="width:640px;height:100px;background-color: {{other}};display: flex;">',
        '                <!-- 字体提示栏 -->',
        '                <div style="width:150px;height:100px;',
        '                            display: flex;align-items: center;font-size: 25px;margin-left: 30px;">',
        '                    保密',
        '                </div>',
        '                <!-- 右箭头 -->',
        '                <div',
        '                    style="margin-left:420px;height: 100%;width:40px;display: flex;justify-content: center;align-items: center;">',
        '                    <img src="./img/箭头_右.png" style="width:40px;">',
        '                </div>',
        '            </div>',
        '            <div style="width:610px;height:1px;background-color: #f0f0f0;margin-left: 30px;"></div>',
        '            <!-- 保存按钮栏 -->',
        '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
        '                <div onclick="SlagoModel.UserPersonal.UserData.sexSetingPage.submit(this);" class="hoverPointer" style="width:120px;height:60px;border-radius:30px;',
        '                                    background-color: #0066cc;color: #ffffff;display: flex;',
        '                                    justify-content: center;align-items: center;font-size: 24px;margin-left: 505px;">',
        '                    保存',
        '                </div>',
        '            </div>',
        '        </div>',
        '    </div>',
    ].join("");
    namespace.getModel = function () {
        return Slagolib.template.engin(this.Page, {});
    }
    /*AJAX请求性别信息后显示性别*/
    namespace.setSex=function(sex){
        let nodes = [
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.man"),
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.woman"),
            document.getElementById("SlagoModel.UserPersonal.UserData.sexSetingPage.other")
        ];
        switch (sex){
            case "男":
                nodes[0].style.backgroundColor="rgb(180, 218, 253)";
                break;
            case "女":
                nodes[1].style.backgroundColor="rgb(180, 218, 253)";
                break;
            case "保密":
                nodes[2].style.backgroundColor="rgb(180, 218, 253)";
                break;
        }
    }
    namespace.show = function () {
        //数据
        let DOMData={
            men:"#ffffff",
            women:"#ffffff",
            other:"#ffffff"
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起get请求获取用户性别
        axios.get(Slago.Data.ServerData.IP+
            "getUserSex?id="+Slago.CookieTool.getCookie("id")).then(response=>{
                this.setSex(response.data.result);
        }).catch(error=>{
            console.log("Slago::ERROR[请求性别信息失败] userDataPage.js");
        });

    }
    //点击保存按钮：请求更改性别
    namespace.submit=function(button){
        //判断现在的性别
        let newSex="";
        let ids = [
            "SlagoModel.UserPersonal.UserData.sexSetingPage.man",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.woman",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.other"
        ];
        if(document.getElementById(ids[0]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="男";
        }else if(document.getElementById(ids[1]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="女";
        }else if(document.getElementById(ids[2]).style.backgroundColor=="rgb(180, 218, 253)"){
            newSex="保密";
        }
        Slago.LoadPage.hover();
        //@AJAX 更改性别请求
        axios.get(Slago.Data.ServerData.IP+
            "setUserSex?newSex="+newSex).then(response=>{
            Slago.LoadPage.trans();
            setTimeout("Slago.LoadPage.move();",500);
        }).catch(error=>{
            Slago.LoadPage.move();
            console.log("Slago::ERROR[请求修改性别失败] sexSetingPage.js");
        });
    }


    //选择按钮:选择按钮 切换节点颜色
    namespace.choose = function (dom) {
        //获得选择按钮的三个节点
        let list = [];
        let ids = [
            "SlagoModel.UserPersonal.UserData.sexSetingPage.man",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.woman",
            "SlagoModel.UserPersonal.UserData.sexSetingPage.other"
        ];
        for (let i = 0; i < ids.length; i++) {
            let obj = {};
            obj.id = ids[i];
            obj.node = document.getElementById(ids[i]);
            list.push(obj);
        }
        for (let i = 0; i < list.length; i++) {
            if (dom.id != list[i].id) {
                list[i].node.style.backgroundColor = "#ffffff";
            } else {
                list[i].node.style.backgroundColor = "rgb(180, 218, 253)";
            }
        }
    }

    //加入模块
    SlagoModel.UserPersonal.UserData.sexSetingPage = namespace;
})();
//=>SlagoModel.UserPersonal.UserData.sexSetingPage=>end
//=>./js/UserPersonal/UserData/signature.js
//=>SlagoModel.UserPersonal.UserData.signature=>start
//个性签名设置
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
        '    <!-- 单个资料信息设置页 -->',
        '    <div style="width:640px;height:600px;background-color: #ffffff;">',
        '        <!-- 导航栏 -->',
        '        <div',
        '            style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
        '            <!-- 返回按键 -->',
        '            <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
        '                style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
        '                <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
        '                <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
        '                    <span style="font-size: 27px;color:#0066cc;">返回</span>',
        '                </div>',
        '            </div>',
        '            <!--导航栏字体栏-->',
        '            <div',
        '                style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
        '                个性签名',
        '            </div>',
        '        </div>',
        '        <div style="height:80px;width:100%;"></div>',
        '         <div style="width:640px;height:400px;">',
        '            <textarea style="width: 600px;height:200px;outline: none;resize: none;',
        '                             color:rgb(36, 36, 36);padding: 10px;background-color: #ffffff;',
        '                             font-size: 26px;margin-left: 10px;">{{profile}}</textarea>',
        '            <!-- 保存按钮栏 -->',
        '            <div style="width:640px;height:100px;background-color: #ffffff;margin-top: 40px;">',
        '                <div onclick="SlagoModel.UserPersonal.UserData.signature.submit(this);" class="hoverPointer" style="width:120px;height:60px;border-radius:30px;',
        '                                                                            background-color: #0066cc;',
        '                                                                            color: #ffffff;display: flex;',
        '                                                                            justify-content: center;align-items: center;',
        '                                                                            font-size: 24px;margin-left: 505px;">',
        '                保存',
        '                </div>',
        '            </div>',
        '        </div>',
        '    </div>'
    ].join("");
    namespace.getModel = function (DOMData) {
        return Slagolib.template.engin(this.Page, DOMData);
    }
    namespace.show = function () {
        //数据
        let DOMData={
            profile:"",
        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //@AJAX 发起GET请求获取用户个性签名
        axios.get(Slago.Data.ServerData.IP+
            "getUserProfile?id="+Slago.CookieTool.getCookie("id")).then(response=>{
            DOMData.profile=response.data.result;
            //重新渲染页面
            PAGENODE.innerHTML=this.getModel(DOMData);
        }).catch(error=>{
            console.log("Slago::ERROR[请求个性签名信息失败] signature.js");
        });
    }
    //提交个性签名
    namespace.submit=function(buttonNode){
        //获得testarea dom node
        let textarea=buttonNode.parentNode.parentNode.children[0];
        //@AJAX更新个性签名
        Slago.LoadPage.hover();
        axios.get(Slago.Data.ServerData.IP+
            "/setUserProfile?"+
            "newProfile="+textarea.value).then(function(response){
            setTimeout("Slago.LoadPage.trans();",300);
        }).catch(error=>{
            Slago.LoadPage.move();//失败则去掉加载页
        })
    }
    //加入模块
    SlagoModel.UserPersonal.UserData.signature=namespace;
})();
//=>SlagoModel.UserPersonal.UserData.signature=>end
SlagoModel.AboutPage={};

//=>./js/AboutPage/Header.js
(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面Header-->',
    '<div style="width:640px;height:150px;position:fixed;background-color:#ffffff;border-bottom:1px rgb(77, 160, 255) solid;">',
    '    <div style="width:640px;height:14px;background-color:#ffffff;"></div>',
    '    <!-- 标题与搜索栏 -->',
    '    <div style="width:640px;height:50%;display: flex;flex-wrap: wrap;">',
    '        <div style="width:140px;background-color:rgb(255, 255, 255);height:100%;',
    '                    font-size:45px;font-weight:bold;display:flex;justify-content: center;',
    '                    align-items: center;color:#11121b;">关注</div>',
    '        <!-- 帖子上传栏 -->',
    '        <div style="width:500px;height:100%;background-color: #ffffff;display: flex;align-items: center;flex-wrap: wrap;">',
    '           <img src="./img/312.png" style="height:40px;margin-left: 415px;" class="hoverPointer" onclick="SlagoModel.PostUpPage.postupPage.show();">',//点击显示帖子上传界面
    '        </div>',
    '    </div>',
    '    <!-- 页面内选择栏 -->',
    '    <div style="width:640px;height:40%;background-color: rgb(255, 255, 255);">',
    '        <!-- 字体栏 -->',
    '        <div style="width:640px;height:80%;background-color: #ffffff;">',
    '            <div style="font-size: 25px;height:100%;color:#0066cc;display: flex;align-items: center;',
    '            margin-left: 23px;">关注</div>',
    '        </div>',
    '        <!-- 滑动条栏 -->',
    '        <div style="width:640px;height:10px;background-color: #ffffff;">',
    '            <div style="width:50px;height:6px;background-color: #0066cc;',
    '            border-radius:3px;margin-left: 24px;"></div>',
    '        </div>',
    '    </div>',
    '</div>',
    '<!--空白-->',
    '<div style="height:150px;width:640px;"></div>'
    ].join("");
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,{});
    };
    //加入模块
    SlagoModel.AboutPage.Header=namespace;
})();
//=>./js/AboutPage/aboutPage.js
(function(){
    //建立命名空间
    let namespace={};
    //发现页
    namespace.template=[
    '<!--页面容器-->',
    '<div style="width:640px;background-color:#ffffff;">',
    '    <!--页面Header-->',
    '    {{HeaderTemplate}}',
    '    <!--帖子流-->',
    '    {{PostStream}}',
    '</div>',
    ].join("");
    namespace.data={
        HeaderTemplate:SlagoModel.AboutPage.Header.getModel(),//获得导航栏
        PostStream:SlagoModel.FindPage.post_model.getModel(),//获得帖子流
    }
    namespace.getModel=function(){
       return Slagolib.template.engin(this.template,this.data);
    };
    //加入模块
    SlagoModel.AboutPage.aboutPage=namespace;
})();
SlagoModel.PostUpPage={};

//=>./js/PostUpPage/postupPage.js
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    namespace.Page=[
    '    <!-- 个人成就页组件 -->',
    '    <div style="width:640px;background-color: #ffffff;">',
    '        <!-- 导航栏 -->',
    '        <div style="width:640px;height:80px;background-color: rgb(255, 255, 255);align-items: center;display: flex;position: fixed;top:0px;border-bottom: 1px solid rgb(77, 160, 255);">',
    '            <!-- 返回按键 -->',
    '            <div class="hoverPointer" onclick="Slago.PageStack.pop()"',
    '                style="display: flex;height:100%;align-items: center;margin-left: 10px;">',
    '                <img src="./img/页面栈返回左箭头.png" style="height:40px;width:40px;">',
    '                <div style="height:100%;display: flex;align-items: center;margin-left: 3px;">',
    '                    <span style="font-size: 27px;color:#0066cc;">返回</span>',
    '                </div>',
    '            </div>',
    '            <!--导航栏字体栏-->',
    '            <div style="width:300px;height:100%;background-color:#ffffff;margin-left:66px;display:flex;align-items:center;justify-content: center;font-size:27px;">',
    '                发帖',
    '            </div>',
    '        </div>',
    '        <div style="height:80px;"></div>',
    '        <!-- 文字区域栏 -->',
    '        <div style="width:640px;margin-top: 10px;">',
    '            <textarea style="width: 600px;height:200px;outline: none;resize: none;color:rgb(36, 36, 36);padding: 10px;',
    '            background-color: #ffffff;font-size: 26px;margin-left: 10px;" placeholder="分享生活美好..."></textarea>',
    '        </div>',
    '        <!-- 图片添加与显示栏 -->',
    '        <div style="width:600px;margin-left: 20px;background-color: #ffffff;margin-top: 20px;">',
    '            <!-- 第一行 -->',
    '            <div style="height:220px;width:600px;background-color: rgb(255, 255, 255);display: flex;align-items: center;justify-content: space-around;">',
    '                <!-- 第一张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)" class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '                <!-- 第二张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)"  class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '                <!-- 第三张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)"  class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '            </div>',
    '            <!-- 第二行 -->',
    '            <div style="height:220px;width:600px;background-color: rgb(255, 255, 255);display: flex;align-items: center;justify-content: space-around;">',
    '                <!-- 第四张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)"  class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '                <!-- 第五张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)"  class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '                <!-- 第六张 -->',
    '                <div onclick="SlagoModel.PostUpPage.PostUpModel.click(this)"  class="hoverPointer" style="width:180px;height:180px;background-color: #fafafa;border-radius: 10px;',
    '                display: flex;justify-content: center;align-items: center;overflow: hidden;">',
    '                    <img src="./img/67.png" style="width:60px;height:60px;">',
    '                    <input type="file" accept="image/*" style="display: none;">',
    '                </div>',
    '            </div>',
    '        </div>',
    '        <!-- 提交按钮 -->',
    '        <div  style="width:640px;height:100px;background-color: rgb(255, 255, 255);margin-top: 40px;display: flex;align-items: center;">',
    '            <div onclick="SlagoModel.PostUpPage.PostUpModel.dataPost(this)" class="hoverPointer" style="width:120px;height:60px;background-color: #0066cc;',
    '            border-radius: 30px;margin-left: 455px;display: flex;',
    '            justify-content: center;align-items: center;',
    '            font-size: 24px;color:#ffffff;">',
    '                发布',
    '            </div>',
    '        </div>',
    '    </div>',
    ].join("");
    namespace.getModel=function(){
        return Slagolib.template.engin(this.Page,{});
    }
    namespace.show=function(){
        //创建页面,推入页面栈
        Slago.CreatePage(this.getModel());
    }
    //加入模块
    SlagoModel.PostUpPage.postupPage=namespace;
})();
//=>./js/PostUpPage/PostUpModel.js
(function(){
    //建立命名空间
    let namespace={};
    //图片添加点击事件
    namespace.click=function(addDivNode){
    //获得此节点下的input-file
    let input_file=addDivNode.children[1];
    //为input_file监听文件上传事件
    input_file.onchange=function(){
        //获得img标签节点
        let img_node=this.parentNode.children[0];
        if(this.files!=undefined&&this.files.length>0&&this.files&&this.files[0]){
            if(this.files[0].getAsDataURL){
                img_node.src=this.files[0].getAsDataURL;
            }else{
                img_node.src=window.URL.createObjectURL(this.files[0]);
            }
            //更新输入图片style
            //获得真实图片高度与宽度
            let imgSize={
                width:img_node.naturalWidth,
                height:img_node.naturalHeight
            };
            //决策
            if(imgSize.width>=imgSize.height){//横长
                img_node.style.height="100%";
                img_node.style.width="auto";
            }else{//竖长
                img_node.style.width="100%";
                img_node.style.height="auto";
            }


        }else if(input_file.value){
            img_node.src=input_file.value;
        }else{
            //将图片还原为加号
            img_node.src="./img/67.png";
            //还原style
            img_node.style.width="60px";
            img_node.style.height="60px";
        }
    }
    input_file.click();
};
//数据上传事件,发布按钮点击事件
namespace.dataPost=function(dom){
    dom=dom.parentNode;//dom为按钮的父节点
    let img_file_list=[];
    let rows=[dom.parentNode.children[3].children[0],
    dom.parentNode.children[3].children[1] ];
    for(let i=0;i<2;i++){
        let row=rows[i];
        for(let i=0;i<row.children.length;i++){
            img_file_list.push(row.children[i].children[1]);
        }
    }
    //得到6个input标签,到img_file_list
    //遍历input标签
    //创建formData
    let formData=new FormData();
    let now_index=0;
    for(let i=0;i<img_file_list.length;i++){
        //判断是否有文件
        let status=(img_file_list[i].files&&img_file_list[i].files.length>0)||img_file_list[i].value;
        if(status){
            //推进formData
            formData.append("img"+now_index.toString(),img_file_list[i]);
            now_index++;
        }
    }
    
    //获取textarea内容
    let textarea_node=dom.parentNode.children[2].children[0];
    //textarea.value 加入formData
    formData.append("textarea",textarea_node.value);
    //调用上传属性
    this.ajax(formData);
};
namespace.ajax=function(formData){
    console.log("帖子内容ajax上传");
    console.log(formData);
    //获得浮层dom
    //添加上传进行可视化
    //显示加载浮层
    Slago.LoadPage.hover();
    //1秒后返回上级
    setTimeout("Slago.LoadPage.trans();setTimeout('Slago.PageStack.pop();',500);",1000);
};
    //加入模块
    SlagoModel.PostUpPage.PostUpModel=namespace;
})();
SlagoModel.LoginModel={};

//=>./js/LoginModel/loginPage.js
//=>SlagoModel.LoginModel.loginPage=>start
(function(){
    //建立命名空间
    let namespace={};
    //渲染页
    // namespace.Page=[
    //
    // ].join("");
    namespace.Page= `
    <div style="width:640px;background-color: #0066cc;display: flex;align-items: center;">
        <div style="width:640px;height:640px;margin-top: 100px;
            display: flex;justify-content: center;
            align-items:center;flex-wrap: wrap;">
            <!--logo区域-->
            <div style="width:400px;
                        height:200px;
                        display: flex;justify-content: center;align-items: center;
                        font-size: 56px;font-weight: bold;color: #fafafa;">
                       Hi! Slago
            </div>
            <!--账号密码区域-->
            <div style="width:640px;height:360px;">
                <!--账号-->
                <input type="text" placeholder="账号" 
                                style="
                                width:520px;margin-left: 60px;
                                height:80px;border-radius: 40px;
                                outline: none;text-align: center;
                                font-size: 28px;">
                <!--密码-->
                <input type="password" placeholder="密码" 
                                style="
                                width:520px;margin-left: 60px;margin-top: 30px;
                                height:80px;border-radius: 40px;
                                outline: none;text-align: center;
                                font-size: 28px;">
                <!--登录按钮-->                  
                 <div onclick="SlagoModel.LoginModel.loginPage.login(this);" class="hoverPointer" style="width:120px;height:60px;border: 2px solid gold;
                                                  border-radius:30px;margin-top: 40px;
                                                  background-color: #0066cc;font-weight: bold;
                                                  color: #ffffff;display: flex;
                                                  justify-content: center;align-items: center;
                                                  font-size: 24px;margin-left: 460px;">
                   登录
                 </div>
                 <!--账号安全中心-->
                 <div class="hoverPointer" style="width:250px;margin-left: 50px;
                            height:50px;display: flex;justify-content: center;
                            align-items: center;font-size: 23px;color: aliceblue;">
                    注册账号或忘记密码
                 </div>
            </div>
            
        </div>

    </div>
    `;
    namespace.login=function(button){
        /*首先进行AJAX请求，进行身份验证，如果验证成功，则直接离开登录界面进行信息刷新与请求
        * 否则留下提示请输入账号与密码,在让用户进行请求登录，获取个人信息
        */
        //AJAX身份验证:验证成功则更新身份信息，否则停止不跳转，让用户登录
        //获取账号与密码input node
        let input_id=button.parentNode.children[0];
        let input_password=button.parentNode.children[1];
        //@AJAX 发起GET请求登录
        Slago.LoadPage.hover();
        axios.get(Slago.Data.ServerData.IP+
            "SlagoService_Login?id="+input_id.value.trim()+
            "&password="+input_password.value.trim()).then(response=>{
            console.log(response.data);
            if(response.data.result=="true"){//验证成功
                Slago.LoadPage.move();
                this.loginSuccess();//登录成功
            }else{
                Slago.LoadPage.move();
            }
        }).catch(error=>{
            Slago.LoadPage.move();
            console.log("Slago::ERROR[登录失败] nameSetingPage.js");
        });
    }
    namespace.getModel = function (DOMData) {
        return Slagolib.template.engin(this.Page, DOMData);
    }
    namespace.show = function () {
        Slago.backgroundColor.seting("#0066cc");
        //数据
        let DOMData={

        };
        //创建页面，推入页面栈
        let PAGENODE=Slago.CreatePage(this.getModel(DOMData));
        //返回钩子(BACK HOOK)
        PAGENODE.setBlock=function(){
            //-----FINAL
            namespace.show();//另起页面
            Slago.PageStack.deleteTwoLast();//干掉原来的此页的老页面
            //-----FINAL
        };
        PAGENODE.setNone=function(){
            Slago.backgroundColor.seting("#ffffff");//恢复背景颜色
            Slago.PageStack.deleteDOMNode(this);
            this.style.display="none";
        }
        /*@AJAX身份验证
        * */
        axios.get(Slago.Data.ServerData.IP+
            "SlagoService_Authentication").then(response=>{
                if(response.data.result=="true"){
                    this.loginSuccess();
                }
        }).catch(error=>{
            console.log("SlagoError::身份校验请求异常");
        });
    }
    namespace.loginSuccess=function(){
        //成功TODO:
        //初始化三个主界面
        //渲染瀑布流
        document.getElementById("Slago.FindPage").innerHTML = SlagoModel.FindPage.findPage.getModel();
        //渲染瀑布流
        document.getElementById("Slago.AboutPage").innerHTML = SlagoModel.AboutPage.aboutPage.getModel();
        //渲染个人主页
        SlagoModel.UserPersonal.my_page.show();
        //退出登录页面
        Slago.PageStack.pop();
        //恢复背景颜色
        Slago.backgroundColor.seting("#ffffff");
    }
    //加入模块
    SlagoModel.LoginModel.loginPage=namespace;
})();
//=>SlagoModel.LoginModel.loginPage=>end