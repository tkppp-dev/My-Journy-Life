<template>
  <div class="editor-wrapper">
    <div class="menubar">
      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('bold') }"
        @click="editor.chain().focus().toggleBold().run()"
      >
        <bold-icon />
      </button>
      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('italic') }"
        @click="editor.chain().focus().toggleItalic().run()"
      >
        <italic-icon />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('strike') }"
        @click="editor.chain().focus().toggleStrike().run()"
      >
        <strike-icon />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('underline') }"
        @click="editor.chain().focus().toggleUnderline().run()"
      >
        <underline-icon />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('code') }"
        @click="editor.chain().focus().toggleCode().run()"
      >
        <text-wrapping-icon name="code" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
        @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
      >
        <h1-icon name="h1" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"
        @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
      >
        <h2-icon name="h2" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"
        @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
      >
        <h3-icon name="h3" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('bulletList') }"
        @click="editor.chain().focus().toggleBulletList().run()"
      >
        <ul-icon name="ul" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('orderedList') }"
        @click="editor.chain().focus().toggleOrderedList().run()"
      >
        <ol-icon name="ol" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('blockquote') }"
        @click="editor.chain().focus().toggleBlockquote().run()"
      >
        <quote-icon name="quote" />
      </button>

      <button
        class="menubar__button"
        :class="{ 'is-active': editor.isActive('codeBlock') }"
        @click="editor.chain().focus().toggleCodeBlock().run()"
      >
        <code-icon name="code" />
      </button>

      <button
        class="menubar__button"
        @click="editor.chain().focus().setHorizontalRule().run()"
      >
        <hr-icon name="hr" />
      </button>

      <button
        class="menubar__button"
        @click="editor.chain().focus().undo().run()"
      >
        <undo-icon name="undo" />
      </button>

      <button
        class="menubar__button"
        @click="editor.chain().focus().redo().run()"
      >
        <redo-icon name="redo" />
      </button>
    </div>
    <div class="editor" @click="focusEditerContent">
      <editor-content
        class="editor__content"
        autocomplete="off"
        autocorrect="off"
        autocapitalize="off"
        spellcheck="false"
        :editor="editor"
        @click.stop
      />
    </div>
  </div>
</template>

<script>
import { Editor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import Underline from '@tiptap/extension-underline';
import Placeholder from '@tiptap/extension-placeholder'
import BoldIcon from 'vue-material-design-icons/FormatBold.vue';
import ItalicIcon from 'vue-material-design-icons/FormatItalic.vue';
import StrikeIcon from 'vue-material-design-icons/FormatStrikethrough.vue';
import UnderlineIcon from 'vue-material-design-icons/FormatUnderline.vue';
import TextWrappingIcon from 'vue-material-design-icons/FormatTextWrappingOverflow.vue';
import H1Icon from 'vue-material-design-icons/FormatHeader1.vue';
import H2Icon from 'vue-material-design-icons/FormatHeader2.vue';
import H3Icon from 'vue-material-design-icons/FormatHeader3.vue';
import UlIcon from 'vue-material-design-icons/FormatListBulleted.vue';
import OlIcon from 'vue-material-design-icons/FormatListNumbered.vue';
import QuoteIcon from 'vue-material-design-icons/FormatQuoteClose.vue';
import CodeIcon from 'vue-material-design-icons/CodeTags.vue';
import HrIcon from 'vue-material-design-icons/Minus.vue';
import UndoIcon from 'vue-material-design-icons/Undo.vue';
import RedoIcon from 'vue-material-design-icons/Redo.vue';

export default {
  name: 'Editor',
  components: {
    EditorContent,
    BoldIcon,
    ItalicIcon,
    StrikeIcon,
    UnderlineIcon,
    TextWrappingIcon,
    H1Icon,
    H2Icon,
    H3Icon,
    UlIcon,
    OlIcon,
    QuoteIcon,
    CodeIcon,
    HrIcon,
    UndoIcon,
    RedoIcon,
  },
  props: {
    initialContent: {
      type: String,
      default: '',
    },
    activeButtons: {
      type: Array,
      default: () => [
        'bold',
        'italic',
        'strike',
        'underline',
        'code',
        'h1',
        'h2',
        'h3',
        'bulletList',
        'orderedList',
        'blockquote',
        'codeBlock',
        'horizontalRule',
        'undo',
        'redo',
      ],
    },
  },
  emits: ['update'],
  data() {
    return {
      html: '',
      json: '',
      editor: null,
    };
  },
  created() {
    this.editor = new Editor({
      content: this.initialContent,
      extensions: [StarterKit, Underline, Placeholder.configure({
        placeholder: '내용을 입력하세요'
      })],
    });

    this.html = this.editor.getHTML();
    this.json = this.editor.getJSON();

    this.editor.on('update', () => {
      this.html = this.editor.getHTML();
      this.json = this.editor.getJSON();
      this.$emit('update', this.html);
    });
  },
  beforeUnmount() {
    this.editor.destroy();
  },
  methods: {
    focusEditerContent(){
      
      this.editor.commands.focus('end')
    }
  }
};
</script>

<style scoped>
.editor-wrapper {
  margin: 0 auto;
  width: 80%;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
}
</style>
