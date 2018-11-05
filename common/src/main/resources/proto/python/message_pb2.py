# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: message.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='message.proto',
  package='',
  syntax='proto3',
  serialized_pb=_b('\n\rmessage.proto\"\x87\x03\n\x0fProtocolMessage\x12*\n\x07request\x18\x01 \x01(\x0b\x32\x19.ProtocolMessage.TRequest\x12,\n\x08response\x18\x02 \x01(\x0b\x32\x1a.ProtocolMessage.TResponse\x1an\n\x08TRequest\x12-\n\x07reqType\x18\x01 \x01(\x0e\x32\x1c.ProtocolMessage.RequestType\x12\x16\n\x05login\x18\x02 \x01(\x0b\x32\x07.CLogin\x12\x1b\n\x04\x63hat\x18\x03 \x01(\x0b\x32\r.CPrivateChat\x1ar\n\tTResponse\x12.\n\x08respType\x18\x01 \x01(\x0e\x32\x1c.ProtocolMessage.RequestType\x12\x18\n\x04resp\x18\x02 \x01(\x0b\x32\n.SResponse\x12\x1b\n\x04\x63hat\x18\x03 \x01(\x0b\x32\r.CPrivateChat\"6\n\x0bRequestType\x12\t\n\x05LOGIN\x10\x00\x12\x08\n\x04\x43HAT\x10\x01\x12\x08\n\x04PING\x10\x02\x12\x08\n\x04PONG\x10\x03\"\xb6\x02\n\x0c\x43PrivateChat\x12\x0e\n\x06userId\x18\x01 \x01(\t\x12\x0e\n\x06\x64\x65stId\x18\x02 \x01(\t\x12\x0f\n\x07\x63ontent\x18\x03 \x01(\x0c\x12(\n\x08\x63hatType\x18\x04 \x01(\x0e\x32\x16.CPrivateChat.ChatType\x12(\n\x08\x64\x61taType\x18\x05 \x01(\x0e\x32\x16.CPrivateChat.DataType\x12\x0e\n\x06seqNum\x18\x06 \x01(\x05\x12\n\n\x02ts\x18\x07 \x01(\x03\x12\r\n\x05msgId\x18\x08 \x01(\t\x12\x0f\n\x07\x65xtName\x18\t \x01(\t\">\n\x08\x44\x61taType\x12\x07\n\x03TXT\x10\x00\x12\t\n\x05VOICE\x10\x01\x12\t\n\x05VIDEO\x10\x02\x12\x07\n\x03IMG\x10\x03\x12\n\n\x06HYBRID\x10\x04\"%\n\x08\x43hatType\x12\x0b\n\x07ONE2ONE\x10\x00\x12\x0c\n\x08ONE2MANY\x10\x01\"\xc3\x01\n\x06\x43Login\x12\r\n\x05msgId\x18\x01 \x01(\t\x12\x0e\n\x06userId\x18\x02 \x01(\t\x12\x0b\n\x03pwd\x18\x03 \x01(\t\x12&\n\ndeviceType\x18\x04 \x01(\x0e\x32\x12.CLogin.DeviceType\x12\n\n\x02ts\x18\x05 \x01(\x03\x12\x0f\n\x07version\x18\x06 \x01(\x05\x12\x0e\n\x06seqNum\x18\x07 \x01(\x05\"8\n\nDeviceType\x12\x0b\n\x07\x41NDROID\x10\x00\x12\x07\n\x03IOS\x10\x01\x12\x0b\n\x07WINDOWS\x10\x02\x12\x07\n\x03MAC\x10\x03\"L\n\tSResponse\x12\x0c\n\x04\x63ode\x18\x01 \x01(\x05\x12\x0c\n\x04\x64\x65sc\x18\x02 \x01(\t\x12\x13\n\x0b\x63\x65rtificate\x18\x03 \x01(\t\x12\x0e\n\x06userId\x18\x04 \x01(\tB\nB\x08Protocolb\x06proto3')
)



_PROTOCOLMESSAGE_REQUESTTYPE = _descriptor.EnumDescriptor(
  name='RequestType',
  full_name='ProtocolMessage.RequestType',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='LOGIN', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='CHAT', index=1, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='PING', index=2, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='PONG', index=3, number=3,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=355,
  serialized_end=409,
)
_sym_db.RegisterEnumDescriptor(_PROTOCOLMESSAGE_REQUESTTYPE)

_CPRIVATECHAT_DATATYPE = _descriptor.EnumDescriptor(
  name='DataType',
  full_name='CPrivateChat.DataType',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='TXT', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='VOICE', index=1, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='VIDEO', index=2, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='IMG', index=3, number=3,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='HYBRID', index=4, number=4,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=621,
  serialized_end=683,
)
_sym_db.RegisterEnumDescriptor(_CPRIVATECHAT_DATATYPE)

_CPRIVATECHAT_CHATTYPE = _descriptor.EnumDescriptor(
  name='ChatType',
  full_name='CPrivateChat.ChatType',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='ONE2ONE', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='ONE2MANY', index=1, number=1,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=685,
  serialized_end=722,
)
_sym_db.RegisterEnumDescriptor(_CPRIVATECHAT_CHATTYPE)

_CLOGIN_DEVICETYPE = _descriptor.EnumDescriptor(
  name='DeviceType',
  full_name='CLogin.DeviceType',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='ANDROID', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='IOS', index=1, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='WINDOWS', index=2, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='MAC', index=3, number=3,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=864,
  serialized_end=920,
)
_sym_db.RegisterEnumDescriptor(_CLOGIN_DEVICETYPE)


_PROTOCOLMESSAGE_TREQUEST = _descriptor.Descriptor(
  name='TRequest',
  full_name='ProtocolMessage.TRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='reqType', full_name='ProtocolMessage.TRequest.reqType', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='login', full_name='ProtocolMessage.TRequest.login', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='chat', full_name='ProtocolMessage.TRequest.chat', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=127,
  serialized_end=237,
)

_PROTOCOLMESSAGE_TRESPONSE = _descriptor.Descriptor(
  name='TResponse',
  full_name='ProtocolMessage.TResponse',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='respType', full_name='ProtocolMessage.TResponse.respType', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='resp', full_name='ProtocolMessage.TResponse.resp', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='chat', full_name='ProtocolMessage.TResponse.chat', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=239,
  serialized_end=353,
)

_PROTOCOLMESSAGE = _descriptor.Descriptor(
  name='ProtocolMessage',
  full_name='ProtocolMessage',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='request', full_name='ProtocolMessage.request', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='response', full_name='ProtocolMessage.response', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[_PROTOCOLMESSAGE_TREQUEST, _PROTOCOLMESSAGE_TRESPONSE, ],
  enum_types=[
    _PROTOCOLMESSAGE_REQUESTTYPE,
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=18,
  serialized_end=409,
)


_CPRIVATECHAT = _descriptor.Descriptor(
  name='CPrivateChat',
  full_name='CPrivateChat',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='userId', full_name='CPrivateChat.userId', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='destId', full_name='CPrivateChat.destId', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='content', full_name='CPrivateChat.content', index=2,
      number=3, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=_b(""),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='chatType', full_name='CPrivateChat.chatType', index=3,
      number=4, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='dataType', full_name='CPrivateChat.dataType', index=4,
      number=5, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='seqNum', full_name='CPrivateChat.seqNum', index=5,
      number=6, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='ts', full_name='CPrivateChat.ts', index=6,
      number=7, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='msgId', full_name='CPrivateChat.msgId', index=7,
      number=8, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='extName', full_name='CPrivateChat.extName', index=8,
      number=9, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _CPRIVATECHAT_DATATYPE,
    _CPRIVATECHAT_CHATTYPE,
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=412,
  serialized_end=722,
)


_CLOGIN = _descriptor.Descriptor(
  name='CLogin',
  full_name='CLogin',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='msgId', full_name='CLogin.msgId', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='userId', full_name='CLogin.userId', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='pwd', full_name='CLogin.pwd', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='deviceType', full_name='CLogin.deviceType', index=3,
      number=4, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='ts', full_name='CLogin.ts', index=4,
      number=5, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='version', full_name='CLogin.version', index=5,
      number=6, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='seqNum', full_name='CLogin.seqNum', index=6,
      number=7, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _CLOGIN_DEVICETYPE,
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=725,
  serialized_end=920,
)


_SRESPONSE = _descriptor.Descriptor(
  name='SResponse',
  full_name='SResponse',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='code', full_name='SResponse.code', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='desc', full_name='SResponse.desc', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='certificate', full_name='SResponse.certificate', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='userId', full_name='SResponse.userId', index=3,
      number=4, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=922,
  serialized_end=998,
)

_PROTOCOLMESSAGE_TREQUEST.fields_by_name['reqType'].enum_type = _PROTOCOLMESSAGE_REQUESTTYPE
_PROTOCOLMESSAGE_TREQUEST.fields_by_name['login'].message_type = _CLOGIN
_PROTOCOLMESSAGE_TREQUEST.fields_by_name['chat'].message_type = _CPRIVATECHAT
_PROTOCOLMESSAGE_TREQUEST.containing_type = _PROTOCOLMESSAGE
_PROTOCOLMESSAGE_TRESPONSE.fields_by_name['respType'].enum_type = _PROTOCOLMESSAGE_REQUESTTYPE
_PROTOCOLMESSAGE_TRESPONSE.fields_by_name['resp'].message_type = _SRESPONSE
_PROTOCOLMESSAGE_TRESPONSE.fields_by_name['chat'].message_type = _CPRIVATECHAT
_PROTOCOLMESSAGE_TRESPONSE.containing_type = _PROTOCOLMESSAGE
_PROTOCOLMESSAGE.fields_by_name['request'].message_type = _PROTOCOLMESSAGE_TREQUEST
_PROTOCOLMESSAGE.fields_by_name['response'].message_type = _PROTOCOLMESSAGE_TRESPONSE
_PROTOCOLMESSAGE_REQUESTTYPE.containing_type = _PROTOCOLMESSAGE
_CPRIVATECHAT.fields_by_name['chatType'].enum_type = _CPRIVATECHAT_CHATTYPE
_CPRIVATECHAT.fields_by_name['dataType'].enum_type = _CPRIVATECHAT_DATATYPE
_CPRIVATECHAT_DATATYPE.containing_type = _CPRIVATECHAT
_CPRIVATECHAT_CHATTYPE.containing_type = _CPRIVATECHAT
_CLOGIN.fields_by_name['deviceType'].enum_type = _CLOGIN_DEVICETYPE
_CLOGIN_DEVICETYPE.containing_type = _CLOGIN
DESCRIPTOR.message_types_by_name['ProtocolMessage'] = _PROTOCOLMESSAGE
DESCRIPTOR.message_types_by_name['CPrivateChat'] = _CPRIVATECHAT
DESCRIPTOR.message_types_by_name['CLogin'] = _CLOGIN
DESCRIPTOR.message_types_by_name['SResponse'] = _SRESPONSE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

ProtocolMessage = _reflection.GeneratedProtocolMessageType('ProtocolMessage', (_message.Message,), dict(

  TRequest = _reflection.GeneratedProtocolMessageType('TRequest', (_message.Message,), dict(
    DESCRIPTOR = _PROTOCOLMESSAGE_TREQUEST,
    __module__ = 'message_pb2'
    # @@protoc_insertion_point(class_scope:ProtocolMessage.TRequest)
    ))
  ,

  TResponse = _reflection.GeneratedProtocolMessageType('TResponse', (_message.Message,), dict(
    DESCRIPTOR = _PROTOCOLMESSAGE_TRESPONSE,
    __module__ = 'message_pb2'
    # @@protoc_insertion_point(class_scope:ProtocolMessage.TResponse)
    ))
  ,
  DESCRIPTOR = _PROTOCOLMESSAGE,
  __module__ = 'message_pb2'
  # @@protoc_insertion_point(class_scope:ProtocolMessage)
  ))
_sym_db.RegisterMessage(ProtocolMessage)
_sym_db.RegisterMessage(ProtocolMessage.TRequest)
_sym_db.RegisterMessage(ProtocolMessage.TResponse)

CPrivateChat = _reflection.GeneratedProtocolMessageType('CPrivateChat', (_message.Message,), dict(
  DESCRIPTOR = _CPRIVATECHAT,
  __module__ = 'message_pb2'
  # @@protoc_insertion_point(class_scope:CPrivateChat)
  ))
_sym_db.RegisterMessage(CPrivateChat)

CLogin = _reflection.GeneratedProtocolMessageType('CLogin', (_message.Message,), dict(
  DESCRIPTOR = _CLOGIN,
  __module__ = 'message_pb2'
  # @@protoc_insertion_point(class_scope:CLogin)
  ))
_sym_db.RegisterMessage(CLogin)

SResponse = _reflection.GeneratedProtocolMessageType('SResponse', (_message.Message,), dict(
  DESCRIPTOR = _SRESPONSE,
  __module__ = 'message_pb2'
  # @@protoc_insertion_point(class_scope:SResponse)
  ))
_sym_db.RegisterMessage(SResponse)


DESCRIPTOR.has_options = True
DESCRIPTOR._options = _descriptor._ParseOptions(descriptor_pb2.FileOptions(), _b('B\010Protocol'))
# @@protoc_insertion_point(module_scope)
