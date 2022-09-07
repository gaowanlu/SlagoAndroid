
/*停止滑动窗口的位置*/
let old_y=document.documentElement.scrollTop||document.body.scrollTop;
let old_x=document.documentElement.scrollLeft||document.body.scrollLeft;
let old_event=window.onscroll;
function ScrollStop(){
    old_y=document.documentElement.scrollTop||document.body.scrollTop;
    old_x=document.documentElement.scrollLeft||document.body.scrollLeft;
    old_event=window.onscroll;//暂存旧的绑定的事件
    window.onscroll=(e)=>{
        e.preventDefault();
        try{
            
        }catch(e){
            
        }
    };
    //console.info("阻止滑动");
    
}

function ScrollStart(){
    window.scrollTo(parseInt(old_x),parseInt(old_y));//恢复Stop之前的状态
    window.onscroll=old_event;
}

export {ScrollStop,ScrollStart};