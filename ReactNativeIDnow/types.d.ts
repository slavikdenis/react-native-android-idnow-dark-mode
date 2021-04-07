import 'react-native';

export type IDnowVideoIdentRejectionCode =
  | 'CANCELLED'
  | 'FAILED'
  | 'INTERNAL_ERROR';

export type IDnowVideoIdentOptions = {
  companyId: string;
  transactionToken: string;
  userInterfaceLanguage: string;
  showVideoOverviewCheck: boolean;
};

export interface IDnowModuleInterface {
  startVideoIdent: (options: IDnowVideoIdentOptions) => Promise<true>;
}

declare module 'react-native' {
  interface NativeModulesStatic {
    IDnowModule: IDnowModuleInterface;
  }
}
