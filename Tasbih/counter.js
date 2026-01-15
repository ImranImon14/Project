let count=0;
let count2=0;
let count3=0;
function increase(){
    count+=1;
    if(count>=100){
        if(count%100==0 ){
        count2++;
        count=0;
        if(count2>=10){
            if(count2%10==0){
                count3++;

        }
    }

    }
}
    updateCounter();
}

function reset(){
    count=0;
    count2=0;
    count3=0;
    updateCounter();
}

function updateCounter(){
    document.getElementById("counter").innerHTML="My Count: "+count;
    document.getElementById("counter2").innerHTML="Full Tasbih Count:"+count2;
    document.getElementById("counter3").innerHTML="Spiritual Goal Complete:"+count3;
}