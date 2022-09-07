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