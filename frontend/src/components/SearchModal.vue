<template>
  <base-modal :show="show">
    <template v-slot:modal-content>
      <div class="container" @click.stop>
        <div class="content">
          <input
            class="search-bar"
            type="text"
            placeholder="리뷰를 검색해보세요!"
          />
        </div>
      </div>
    </template>
  </base-modal>
</template>

<script>
import BaseModal from './BaseModal.vue';
export default {
  name: 'SearchModal',
  components: {
    BaseModal,
  },
  props: {
    show: Boolean,
  },
};
</script>

<style>
.container {
  display: flex;
  margin-top: 112px;
  justify-content: center;
}

.content {
  z-index: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 800px;
  height: 200px;
  border-radius: 12px;
  background-color: rgba(196, 196, 196, 0.2);

  box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.2);
}

.search-bar-icon {
  position: absolute;
}

.search-bar {
  z-index: 2;
  width: 640px;
  height: 50px;
  padding: 12px 12px 12px 56px;
  border: 0px;
  border-radius: 6px;
  font-size: 18px;
  box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.2);

  /* search icon */
  background-image: url('../assets/search_icon.png');
  background-repeat: no-repeat;
  background-position: 16px center;
  background-size: 27px;
}

.search-bar:focus {
  outline: none;
}

.search-bar::placeholder {
  font-size: 20px;
}
</style>
