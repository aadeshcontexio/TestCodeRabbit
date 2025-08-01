/* <!-- ***  UI/CSS/JS Author Neville Doke  *** --> */
/* Side Bar Start */
const body = document.querySelector("body"),
sidebar = body.querySelector(".side_nav"),
mainContent = body.querySelector(".main_content"),
toggle = body.querySelector(".toggle"),
/* searchBtn = body.querySelector(".search-box"), */
modeSwtich = body.querySelector(".toggle-switch"),
modeText = body.querySelector(".mode-text");
	
toggle.addEventListener("click", () =>{
	sidebar.classList.toggle("close");
	mainContent.classList.toggle("open");
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
/* Top Nav Profile Start */	
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
/* Top Nav Profile End */	
/* Get filter panel Start  */
const closeBtn = document.querySelector('.close-btn');
const filterBtn = document.querySelector('.filter-btn');
const filterPanel = document.querySelector('.filter-panel');

// Show/Hide filter panel on filter button click
filterBtn.addEventListener('click', () => {
  if (filterPanel.classList.contains('show')) {
    filterPanel.classList.remove('show'); // Slide down and hide
  } else {
    filterPanel.style.display = 'flex'; // Ensure it's visible for animation
    setTimeout(() => {
      filterPanel.classList.add('show'); // Slide up and show
    }, 10); // Small delay to trigger the transition
  }
});

// Hide the filter panel on close button click
closeBtn.addEventListener('click', () => {
  filterPanel.classList.remove('show'); // Slide down
  setTimeout(() => {
    filterPanel.style.display = 'none'; // Hide completely after animation
  }, 500); // Match the transition duration
});
/* Get filter panel End  */

document.querySelectorAll('.enhanced-button').forEach(btn => {
    btn.addEventListener('mousemove', (e) => {
        const rect = btn.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        btn.style.setProperty('--x', `${x}px`);
        btn.style.setProperty('--y', `${y}px`);
        
        const rotX = -(y - rect.height/2) / 8;
        const rotY = (x - rect.width/2) / 4;
        
        btn.style.transform = `rotateX(${rotX}deg) rotateY(${rotY}deg) 
                             translateZ(10px) scale(1.05)`;
    });
    
    btn.addEventListener('mouseleave', () => {
        btn.style.transform = 'rotateX(0) rotateY(0) translateZ(0) scale(1)';
    });
});

document.querySelectorAll('.tag').forEach(tag => {
  tag.addEventListener('mouseenter', () => {
      tag.style.transform = 'translateY(-2px)';
  });
  tag.addEventListener('mouseleave', () => {
      tag.style.transform = 'translateY(0)';
  });
});

// Add floating animation to action buttons
document.querySelectorAll('.tab-actions').forEach(btn => {
  btn.addEventListener('mouseenter', () => {
      btn.style.transform = 'translateY(-2px)';
  });
  btn.addEventListener('mouseleave', () => {
      btn.style.transform = 'translateY(0)';
  });
});
const swiper = new Swiper('.swiper', {
  // Optional parameters
  loop: true,
  slidesPerView: 3,
  spaceBetween: 30,
  navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
  },
  pagination: {
      el: '.swiper-pagination',
      clickable: true,
  },
});



// Share Folder

function shareSelectedFolder(icon) {
  let folderElement = icon.previousElementSibling;
  let folderName = folderElement.dataset.name;
  let folderLink = folderElement.dataset.folderLink;

  if (!folderName || !folderLink) {
      alert("Folder information is missing!");
      return;
  }

  // Remove existing popup if any
  let existingPopup = document.querySelector(".share-popup");
  if (existingPopup) existingPopup.remove();

  // Create and show popup
  let popup = document.createElement("div");
  popup.className = "share-popup";
  popup.innerHTML = `
<div class="share-popup-header">
  <h3>Share Folder</h3>
  <i class='bx bx-x close-popup' onclick="closeSharePopup()"></i>
</div>
<div class="share-popup-content">
  <label>Folder Name</label>
  <input type="text" value="${folderName}" readonly>

  <label>Folder Link</label>
  <textarea id="shareFolderLink" readonly>${folderLink}</textarea>
  <button class="copy-btn" onclick="copyFolderLink()"><i class='bx bxs-copy'></i> Copy Link</button>
  <hr>

  <label>Add Members</label>
  <input type="text" id="memberEmail" placeholder="Enter email">
  <button class="add-member-btn" onclick="addMember()"><i class='bx bxs-user-plus'></i> Add</button>
  <hr>

  <label>Share on Platforms</label>
  <div class="share-buttons">
      <button onclick="shareOn('Amazon')"><i class="bx bxl-amazon"></i> Amazon</button>
      <button onclick="shareOn('Flipkart')"><i class="bx bxl-facebook"></i> Flipkart</button>
      <button onclick="shareOn('Walmart')"><i class="bx bxl-linkedin"></i> Walmart</button>
      <button onclick="shareOn('Myntra')"><i class="bx bxl-instagram"></i> Myntra</button>
  </div>
</div>
`;

  document.body.appendChild(popup);
}

// Function to copy folder link
function copyFolderLink() {
  let folderLink = document.getElementById("shareFolderLink");
  folderLink.select();
  document.execCommand("copy");
  alert("Folder link copied!");
}