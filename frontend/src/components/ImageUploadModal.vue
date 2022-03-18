<template>
  <base-modal :show="show">
    <template v-slot:modal-content>
      <div class="upload-modal-container">
        <div class="upload-modal-wrapper" @click.stop>
          <div class="upload-type-wrapper">
            <div
              class="upload-type-selector"
              :class="{ active: isUploadActive }"
              @click.stop="activateUpload"
            >
              이미지 업로드
            </div>
            <div
              class="upload-type-selector"
              :class="{ active: isLinkActive }"
              @click.stop="activateLink"
            >
              이미지 링크
            </div>
          </div>
          <div class="upload-body" v-if="isUploadActive">
            <input
              class="upload-input"
              ref="image"
              type="file"
              accept="image/*"
            />
            <button class="upload-button" @click="uploadImageFile">
              업로드
            </button>
          </div>
          <div class="upload-body" v-if="isLinkActive">
            <link-icon fillColor="grey" style="paddingtop: 3px" />
            <input
              class="link-input"
              type="text"
              placeholder="이미지 링크를 입력하세요"
            />
            <button class="upload-button" @click="insertImageLink">
              업로드
            </button>
          </div>
        </div>
      </div>
    </template>
  </base-modal>
</template>

<script>
import BaseModal from './BaseModal.vue';
import LinkIcon from 'vue-material-design-icons/Link.vue';
import _axios from '../util/_axios';

export default {
  name: 'ImageUploadModal',
  components: {
    BaseModal,
    LinkIcon,
  },
  props: {
    show: Boolean,
  },
  data() {
    return {
      isUploadActive: true,
      isLinkActive: false,
    };
  },
  methods: {
    activateUpload() {
      this.isUploadActive = true;
      this.isLinkActive = false;
    },
    activateLink() {
      this.isLinkActive = true;
      this.isUploadActive = false;
    },
    async uploadImageFile() {
      const imageData = this.$refs.image.files;

      if (imageData.length === 0) {
        alert('먼저 파일을 업로드해주세요');
      } else {
        const formData = new FormData();

        formData.append('imageFiles', imageData[0]);
        
        try{
          const res = await _axios.post('/api/upload/image/review', formData, {
            headers: {
              'Content-type': 'multipart/form-data',
            },
          });
  
          this.$emit('insertImage', res.data)
          this.$emit('close')
        } catch(e) {
          alert('예상치 못한 문제가 발생했습니다. 다시 시도해주세요')
        }
      }
    },
  },
};
</script>

<style>
.upload-modal-container {
  display: flex;
  justify-content: center;
}

.upload-modal-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 600px;
  margin-top: 120px;

  background-color: white;

  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.4);
}

.upload-type-wrapper {
  display: flex;
  width: 100%;
}
.upload-type-selector {
  flex: 1;

  padding: 10px 0px;

  font-size: 14px;
  font-weight: bold;
  text-align: center;

  cursor: pointer;
  box-sizing: border-box;

  opacity: 0.5;
}

.active {
  opacity: 1;
  border-bottom: 2px solid black;
}

.upload-body {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100px;

  padding: 0 20px;
  box-sizing: border-box;
}

.link-input,
.upload-input {
  flex: 1;
  border: none;
}

.link-input {
  margin-left: 6px;
  padding: 6px 6px;

  border-bottom: 1px solid rgba(0, 0, 0, 0.3);
}

.upload-input {
  padding: 6px 0;
}

.upload-button {
  margin-left: 12px;
  padding: 6px 16px;
  border-radius: 3px;
  font-family: 'nanum-gothic', 'sans-serif';
  font-size: 15px;
  font-weight: 600;
  background-color: #e2d8be;
}
</style>
