export interface User {
  id: string;

  email: string;

  phonePrefix: string | undefined;
  phoneNumber: string | undefined;

  firstName: string;
  lastName: String;

  dateOfBirth: string;

  language: string;

  country: string | undefined;
  city: string | undefined;
  zip: string | undefined;
  street: string | undefined;
  house: string | undefined;

  active: boolean;
}

export interface UpdateUserRequestDto {
  phonePrefix: string | undefined;
  phoneNumber: string | undefined;

  firstName: string;
  lastName: String;

  dateOfBirth: string;

  country: string | undefined;
  city: string | undefined;
  zip: string | undefined;
  street: string | undefined;
  house: string | undefined;
}

export interface UpdateUserPasswordRequestDto {
  password: string;
}

export interface UpdateUserLanguageRequestDto {
  language: string;
}
