//悬浮层
let SuspensionDOM=document.getElementById("suspension");
let Suspension={
    DOM:SuspensionDOM,
    status:'none',
    clear(){
        this.DOM.style.display='none';
        this.DOM.innerHTML='';
    },
    showLoading(){
        this.clear();
        this.status='loading';
        this.DOM.style.display='flex';
        this.DOM.innerHTML=`
            <img style="width:150px" src="/img/loading.gif" />
            <div class="slago-flex-jc-ac" style="width:100%;">拼命进行中、请勿退出...</div>
        `;
    },
};
export {Suspension};