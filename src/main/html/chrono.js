let nbSeconds = 0;
let nbMinutes = 0;
let chrono = null;

function startChrono(){
    console.log("starting chrono...");
    chrono = setInterval(() => {updateTime()}, 1000);
}

function stopChrono(){
    console.log("stoping chrono...");
    clearInterval(chrono);
}

function updateTime(){
    nbSeconds++;
    if (nbSeconds == 60){
        nbSeconds = 0;
        nbMinutes++;
    }
    const duration = `${nbMinutes}min${nbSeconds}s`;
    const chronoHtmlElement = document.getElementById('chrono');
    chronoHtmlElement.textContent = duration;
}