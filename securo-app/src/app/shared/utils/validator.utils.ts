import { Validators } from '@angular/forms';
import { phoneNumberRegex, phonePrefixRegex } from '../const/regex.const';

export const PhonePrefixValidator = Validators.pattern(phonePrefixRegex);
export const PhoneNumberValidator = Validators.pattern(phoneNumberRegex);
