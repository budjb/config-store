package com.budjb.config

import grails.transaction.Transactional

@Transactional
class DictionaryService {
    Dictionary createDictionary(Dictionary dictionary) {
        if (!dictionary.validate()) {

        }
    }
}
