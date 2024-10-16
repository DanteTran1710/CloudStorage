function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId || '');
    $('#note-title').val(noteTitle || '');
    $('#note-description').val(noteDescription || '');
    $('#noteModal').modal('show');
}

function showCredentialModal(credentialId, url, username, key, password) {
    $('#credential-id').val(credentialId || '');
    $('#credential-url').val(url || '');
    $('#credential-username').val(username || '');
    $('#credential-key').val(key || '');
    $('#credential-password').val(password || '');
    $('#credentialModal').modal('show');
}
