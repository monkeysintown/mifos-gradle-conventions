{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  outputs = { self, nixpkgs }:
  let
    system = "x86_64-linux";
    pkgs = nixpkgs.legacyPackages.${system};
    gradle = pkgs.gradle.override {
      version = "9.5.1";
      jdk = pkgs.jdk25;
    };
  in
  {
    devShells.${system}.default = pkgs.mkShell {
      buildInputs = [
        pkgs.graalvmPackages.graalvm-ce
        pkgs.jetbrains.idea-oss
        gradle
        pkgs.jdk25
        pkgs.asciidoctor
        pkgs.libz
        pkgs.libz.dev
      ];
    };
  };
}
