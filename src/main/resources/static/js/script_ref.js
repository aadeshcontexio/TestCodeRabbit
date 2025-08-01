const body = document.querySelector("body"),
sidebar = body.querySelector(".side_nav"),
toggle = body.querySelector(".toggle"),
/* searchBtn = body.querySelector(".search-box"), */
modeSwtich = body.querySelector(".toggle-switch"),
modeText = body.querySelector(".mode-text");
	
toggle.addEventListener("click", () =>{
	sidebar.classList.toggle("close");
});
/* searchBtn.addEventListener("click", () =>{
	sidebar.classList.remove("close");
}); */

modeSwtich.addEventListener("click", () =>{
	body.classList.toggle("dark");
	
	if(body.classList. contains("dark")){
	modeText.innerText = "Light Mode"
	}else{
	modeText.innerText = "Dark Mode"
	}
});
/* Side Bar End */
	

const profile = document.querySelector(".profile"),
profileMenus = document.querySelector(".profile-menus");

profile.addEventListener("click", function (event) {
	event.stopPropagation();
	profileMenus.style.display = profileMenus.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function () {
	profileMenus.style.display = "none";
});

profileMenus.addEventListener("click", function (event) {
	event.stopPropagation();
});

	

	