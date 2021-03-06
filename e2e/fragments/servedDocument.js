const {I} = inject();

module.exports = {

  fields: {
    servedDocumentFiles: {
      id: '#servedDocumentFiles_servedDocumentFiles'
    }
  },

  async upload(file, documents) {
    I.waitForElement(this.fields.servedDocumentFiles.id);
    for (const fileType of documents) {
      await within(fileType, async () => {
        I.click('Add new');
        await I.attachFile(fileType + '_0', file);
        await I.waitForInvisible(locate('.error-message').withText('Uploading...'));
      });
    }
  },
};

