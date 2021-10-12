<template>
<div>
  <div class="q-btn">
    <a>
      <img src="@/assets/buttons/q_mark.png" class="img-fluid" data-bs-toggle="modal" data-bs-target="#tip-modal">
    </a>
  </div>
  <div class="modal fade" id="tip-modal" tabindex="-1" aria-labelledby="tip-modal-label" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="tip-modal-label">@Play 방법 간단 소개</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body p-5">
        <h3 class="mb-3 text-center" style="color: #6a82fb;">
          AI가 본 그림과 내가 그린 그림은
          <br>
          얼마나 비슷할까 🤤 ?
        </h3>
        <p>
        1. 초기 방 생성은 방만들기, 
        <br>
        <span style="margin-left: 1.1rem">
        초대링크를 받은 유저는 입장하기를 클릭합니다.
        </span>
        
        </p>
        <p>
          2. 로비에서 라운드, 게임시간과 인원을 설정합니다.
        </p>
        <p>
          3. RUGI 가 출제해주는 문장을 보고 그림을 그립니다.
        </p>
        <p class="text-secondary" style="margin-left: 1.1rem">
          * RUGI가 여러분의 그림을 해석할 겁니다!
        </p>
        <p>
          4. 서로의 그림을 공유하고, 다음 라운드를 진행합니다.
        </p>
        <p>
          5. 이제... 즐기러 가시죠! 낄낄 :)
        </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-OK" data-bs-dismiss="modal">OK</button>
      </div>
    </div>
</div>
  </div>
</div>
</template>

<script>
export default {
  name: "Modal",
}
</script>

<style>
.q-btn {
  display: flex;
  position: absolute;
  width: 3rem !important;
  height: 3rem !important;
  bottom: 2rem;
  left: 6rem;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50%;
  padding: 1rem;
  align-items: center;
}

.q-btn:hover{
  background-color: #6a82fb45;
  
  border-radius: 50%;
  border: none;
  cursor: pointer;
}
.btn-OK {
  display: flex;
  /* position: absolute; */
  width: 3rem !important;
  height: 3rem !important;
  box-shadow: 0.2rem 0.2rem 0.5rem #cacaca;
  border-radius: 50% !important;
  padding: 1rem;
  color: #6a82fb !important;
  align-items: center;
}
.btn-OK:hover{
  background-color: #6a82fb45;
  border-radius: 50%;
  border: none;
  cursor: pointer;
}
</style>