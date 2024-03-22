module FullAdder (input a, input b, input ci, output so, output co);
    wire s;
    wire [1:0] c;

    xor s0 (s, a, b);
    xor SUM (so, s, ci);
    and c0 (c[0], a, b);
    and c1 (c[1], s, ci);
    or CARRY (co, c[0], c[1]);
endmodule

module FourBitAdder(
    input [3:0] a,
    input [3:0] b,
    output wire [4:0] s);
    
    wire [2:0] c;

    FullAdder FA0(.a(a[0]), .b(b[0]), .ci(), .so(s[0]), .co(c[0]));
    FullAdder FA1(.a(a[1]), .b(b[1]), .ci(c[0]), .so(s[1]), .co(c[1]));
    FullAdder FA2(.a(a[2]), .b(b[2]), .ci(c[1]), .so(s[2]), .co(c[2]));
    FullAdder FA3(.a(a[3]), .b(b[3]), .ci(c[2]), .so(s[3]), .co(s[4]));
endmodule

module Drive7Seg(
    input wire [3:0] S,
    output reg [6:0] D);

    always @(*) begin
        case (S)
            4'b0000: D = 7'b1000000;    // 0
            4'b0001: D = 7'b1111001;    // 1
            4'b0010: D = 7'b0100100;    // 2
            4'b0011: D = 7'b0110000;    // 3
            4'b0100: D = 7'b0011001;    // 4
            4'b0101: D = 7'b0010010;    // 5
            4'b0110: D = 7'b0000010;    // 6
            4'b0111: D = 7'b1111000;    // 7
            4'b1000: D = 7'b0000000;    // 8
            4'b1001: D = 7'b0010000;    // 9
            4'b1010: D = 7'b0001000;    // a
            4'b1011: D = 7'b0000011;    // b
            4'b1100: D = 7'b1000110;    // c
            4'b1101: D = 7'b0100001;    // d
            4'b1110: D = 7'b0000110;    // e
            4'b1111: D = 7'b0001110;    // f
            default: D = 7'b1111111;    // f+1
        endcase
    end
endmodule

module BinaryTo7Seg(
    input wire [3:0] a,
    input wire [3:0] b,
    output wire [6:0] DISPLAY,
    output wire OVERFLOW);

    wire [4:0] S;
    // wire [6:0] D;

    FourBitAdder ADD (
        .a(a),
        .b(b),
        .s(S[4:0])
    );
    Drive7Seg DRIVER (
        .S(S[3:0]),
        .D(DISPLAY[6:0])
    );
    
    assign OVERFLOW = S[4];
    // assign DISPLAY = D[6:0];
endmodule

/*
AN[3:0]
///////////////////////////
//           a           //
//                       //
//           -           //
//   f      | |      b   //
//      g    -           //
//   e      | |      c   //
//           -           //
//                       //  
//           d           //
///////////////////////////

?       S[4:0]                      D[6:0]                  D[0:6]
#:     {16, 8, 4, 2, 1}     -->     {g, f, e, d, c, b, a}   {a, b, c, d, e, f, g}
0:      {0, 0, 0, 0, 0}     -->     {1, 0, 0, 0, 0, 0, 0}   {0, 0, 0, 0, 0, 0, 1}
1:      {0, 0, 0, 0, 1}     -->     {1, 1, 1, 1, 0, 0, 1}   {1, 0, 0, 1, 1, 1, 1}
2:      {0, 0, 0, 1, 0}     -->     {0, 1, 0, 0, 1, 0, 0}   {0, 0, 1, 0, 0, 1, 0}
3:      {0, 0, 0, 1, 1}     -->     {0, 1, 1, 0, 0, 0, 0}   {0, 0, 0, 0, 1, 1, 0}
4:      {0, 0, 1, 0, 0}     -->     {0, 0, 1, 1, 0, 0, 1}   {1, 0, 0, 1, 1, 0, 0}
5:      {0, 0, 1, 0, 1}     -->     {0, 0, 1, 0, 0, 1, 0}   {0, 1, 0, 0, 1, 0, 0}
6:      {0, 0, 1, 1, 0}     -->     {0, 0, 0, 0, 0, 1, 0}   {0, 1, 0, 0, 0, 0, 0}
7:      {0, 0, 1, 1, 1}     -->     {1, 1, 1, 1, 0, 0, 0}   {0, 0, 0, 1, 1, 1, 1}
8:      {0, 1, 0, 0, 0}     -->     {0, 0, 0, 0, 0, 0, 0}   {0, 0, 0, 0, 0, 0, 0}
9:      {0, 1, 0, 0, 1}     -->     {0, 0, 1, 0, 0, 0, 0}   {0, 0, 0, 0, 1, 0, 0}
a:      {0, 1, 0, 1, 0}     -->     {0, 0, 0, 1, 0, 0, 0}   {0, 0, 0, 1, 0, 0, 0}
b:      {0, 1, 0, 1, 1}     -->     {0, 0, 0, 0, 0, 1, 1}   {1, 1, 0, 0, 0, 0, 0}
c:      {0, 1, 1, 0, 0}     -->     {1, 0, 0, 0, 1, 1, 0}   {0, 1, 1, 0, 0, 0, 1}
d:      {0, 1, 1, 0, 1}     -->     {0, 1, 0, 0, 0, 0, 1}   {1, 0, 0, 0, 0, 1, 0}
e:      {0, 1, 1, 1, 0}     -->     {0, 0, 0, 0, 1, 1, 0}   {0, 1, 1, 0, 0, 0, 0}
f:      {0, 1, 1, 1, 1}     -->     {0, 0, 0, 1, 1, 1, 0}   {0, 1, 1, 1, 0, 0, 0}
f+1:    {1, 0, 0, 0, 0}     -->     {1, 1, 1, 1, 1, 1, 1}   {1, 1, 1, 1, 1, 1, 1}
*/
