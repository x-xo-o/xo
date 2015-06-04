package org.x_xo_o.xo

class ConstraintValidator {

    final static NONE = 'No Error'
    final static NULLABLE = 'nullable'
    final static UNIQUE = 'unique'

    static void validateConstraints(validateable, String field, error) {
        validateable.validate()
        if (error && error != NONE) {
            def value = validateable."$field"
            // assert !validated <- This assertion would hide the field
            assert validateable.errors[field] : "expected error: '$error' for $field = $value"
            assert validateable.errors[field].codes
            assert validateable.errors[field].codes.contains(error)
        } else {
            assert !validateable.errors[field]
        }
    }
}
