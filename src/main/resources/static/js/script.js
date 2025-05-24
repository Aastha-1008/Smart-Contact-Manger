console.log("script")

let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded',()=>{
    changeTheme();
});

function changeTheme() {
    
    changePageTheme(currentTheme,currentTheme);
    const changeThemeButton = document.querySelector('#theme_change_button');
    changeThemeButton.querySelector('span').textContent = currentTheme == 'light' ? 'Dark' : 'Light';
    const oldTheme = currentTheme;

    changeThemeButton.addEventListener("click", (event) => {

        console.log("change theme button clicked");
        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }
        changePageTheme(currentTheme,oldTheme);
    });
}

//set theme to localstorage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}


//get theme to localstorage
function getTheme() {
    let theme = localStorage.getItem("theme")
    if (theme) return theme;
    else return "light";
}

function changePageTheme(theme,oldTheme) {
    const changeThemeButton = document.querySelector('#theme_change_button');
    setTheme(theme);
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(theme);
    changeThemeButton.querySelector('span').textContent = theme == 'light' ? 'Dark' : 'Light';

}

