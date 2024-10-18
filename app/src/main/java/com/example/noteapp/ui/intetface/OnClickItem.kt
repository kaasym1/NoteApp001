package com.example.noteapp.ui.intetface

import com.example.noteapp.data.model.NoteModel

interface OnClickItem {

    fun onLongClick(noteModel: NoteModel)
}