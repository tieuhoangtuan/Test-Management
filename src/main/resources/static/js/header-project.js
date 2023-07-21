function ActiveNavigate(){
    const overview = document.getElementById("overview");
    const milestone = document.getElementById("milestone");
    const run = document.getElementById("run");
    const cases = document.getElementById("cases");
    const url = window.location.href;
    console.log(url);
    if(url.includes("/project/overview/")){
        overview.classList.add("item-selected")
    }else if (url.includes("/milestones/")){
        milestone.classList.add("item-selected")
    }else if (url.includes("/runs/") || url.includes("/tests/")){
        run.classList.add("item-selected")
    }else if (url.includes("/suites/") || url.includes("/cases/")){
        cases.classList.add("item-selected")
    }
}
window.addEventListener('load', ActiveNavigate)