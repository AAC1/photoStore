
declare module NodeJS {
    interface Global {
        SALT : string;
        [key: string]: string;
    }
  }

global.SALT = "3d777fc8f098c8c18e457560352c4c619296801b65614cb9e49ec005aee55352";