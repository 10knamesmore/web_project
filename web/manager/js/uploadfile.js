// TODO 
const button = document.getElementById("back_button");
back_button.addEventListener("click", (event) => {
  const container = document.getElementById("upload-container");

  const targetUrl = event.target.getAttribute("data-url");

  setTimeout(() => {
    window.location.href = targetUrl;
  }, 300);
  container.classList.remove("fade_in")
  container.classList.add("fade_out");
  // container.style.transform = ""

});

window.addEventListener("load", () => {
  const container = document.getElementById("upload-container");
  container.classList.add("fade_in");

  const fileInput = document.getElementById("file");
  const fileLabel = document.getElementById("fileLabel");

  fileInput.addEventListener("change", () => {
    if (fileInput.files.length > 0) {
      const fileName = fileInput.files[0].name; // 获取文件名
      fileLabel.textContent = `已选择文件: ${fileName}`;
    } else {
      fileLabel.textContent = "点击选择文件或拖动到此处"; // 恢复默认提示
    }
  });
});
