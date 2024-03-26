import React from "react";
import Header from "@/components/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const RootLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <div className="container-fluid vh-100 p-0">
        {/*<Header />*/}
        <div className="d-flex h-50 justify-content-center align-content-start mt-4">
          {children}
        </div>
      </div>
    </>
  );
};

export default RootLayout;
